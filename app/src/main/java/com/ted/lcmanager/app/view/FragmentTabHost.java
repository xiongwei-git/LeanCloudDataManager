package com.ted.lcmanager.app.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

/***
 * add by Ted
 */
public class FragmentTabHost extends LinearLayout {
	public FrameLayout mFrameLayout;
	public LinearLayout mTabHostLayout;
	public Context mContext;
	public FragmentManager mFragmentManager;
	public boolean mAttached;
	public TabInfo mLastTab;
	public int mCurrentTab;
	public final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
	public TabHostListener mTabHostListener;

    private int mContainerId = -1;

	public static interface TabHostListener {
		/** 切换时触发 */
		public void onTabChange(int postion, String tag);

		/** 重复点击时触发 */
		public void onTabClick(int postion, String tag);
	}

	static final class TabInfo {
		public final String tag;
		public final Class<?> clss;
		public final Bundle args;
		public Fragment fragment;
		public final int indicatorResId;

        public TabInfo(String _tag, Class<?> _class, Bundle _args) {
			this(_tag, _class, _args, 0);
		}

        public TabInfo(String _tag, Class<?> _class, Bundle _args, int _indicatorResId) {
			tag = _tag;
			clss = _class;
			args = _args;
			indicatorResId = _indicatorResId;
		}
	}

	static class TabSavedState extends BaseSavedState {
		String curTab;

        TabSavedState(Parcelable superState) {
			super(superState);
		}

		private TabSavedState(Parcel in) {
			super(in);
			curTab = in.readString();
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeString(curTab);
		}

		@Override
		public String toString() {
			return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + curTab + "}";
		}

		public static final Creator<TabSavedState> CREATOR = new Creator<TabSavedState>() {
			public TabSavedState createFromParcel(Parcel in) {
				return new TabSavedState(in);
			}

			public TabSavedState[] newArray(int size) {
				return new TabSavedState[size];
			}
		};
	}

	public FragmentTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
		initFragmentTabHost(context);
	}

	private void initFragmentTabHost(Context context) {
		mCurrentTab = -1;
		setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
	}

	public void setUp(Context context, FragmentManager manager,int tabHostId,int containerId) {
        mContainerId = containerId;
        mTabHostLayout = (LinearLayout) findViewById(tabHostId);
		if (mTabHostLayout == null) {
			throw new RuntimeException("Your FragmentTabHost must have a mSwitchBtnLayout whose id attribute is 'R.id.tab_host_index'");
		}
		mFrameLayout = (FrameLayout) findViewById(containerId);
		if (mFrameLayout == null) {
			throw new RuntimeException("Your FragmentTabHost must have a FrameLayout whose id attribute is 'R.id.container'");
		}
		mContext = context;
		mFragmentManager = manager;
	}

	public void addTab(String tag, Class<?> clss, Bundle args) {
		addTab(tag, clss, args, 0);
	}

	public void addTab(String tag, Class<?> clss, Bundle args, int indicatorResId) {
		TabInfo info = new TabInfo(tag, clss, args, indicatorResId);
		if (mAttached) {
			// If we are already attached to the window, then check to make
			// sure this tab's fragment is inactive if it exists. This shouldn't
			// normally happen.
			info.fragment = mFragmentManager.findFragmentByTag(tag);
			if (info.fragment != null && !info.fragment.isDetached()) {
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.detach(info.fragment);
				info.fragment = null;
				ft.commit();
			}
		}
		mTabs.add(info);
		if (mCurrentTab == -1) {
			mCurrentTab = 0;
		}
    }

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mAttached = true;
		String currentTab = getCurrentTabTag();
		// Go through all tabs and make sure their fragments match
		// the correct state.
		FragmentTransaction ft = null;
		for (int i = 0; i < mTabs.size(); i++) {
			TabInfo tab = mTabs.get(i);
			tab.fragment = mFragmentManager.findFragmentByTag(tab.tag);
			if (tab.fragment != null && !tab.fragment.isDetached()) {
				if (tab.tag.equals(currentTab)) {
					// The fragment for this tab is already there and
					// active, and it is what we really want to have
					// as the current tab. Nothing to do.
					mLastTab = tab;
				} else {
					if (ft == null) {
						ft = mFragmentManager.beginTransaction();
					}
					ft.detach(tab.fragment);
					tab.fragment = null;
				}
			}
		}
		ft = doTabChanged(currentTab, ft);
		if (ft != null) {
			ft.commitAllowingStateLoss();
			mFragmentManager.executePendingTransactions();
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mAttached = false;
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
        TabSavedState tabSavedState = new TabSavedState(superState);
		return tabSavedState;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
        TabSavedState ss = (TabSavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());
		setCurrentTabByTag(ss.curTab);
	}

	public void setCurrentTabByTag(String tag) {
		int i;
		FragmentTransaction ft = null;
		for (i = 0; i < mTabs.size(); i++) {
			if (mTabs.get(i).tag.equals(tag)) {
				ft = doTabChanged(tag, null);
				mCurrentTab = i;
				break;
			}
		}
		if (ft != null) {
			ft.commit();
			mFragmentManager.executePendingTransactions();
			if (mTabHostListener != null) {
                mTabHostListener.onTabChange(i, tag);
			}

			for (i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i).indicatorResId > 0) {
					View tab = findViewById(mTabs.get(i).indicatorResId);
					if (tab != null) {
						if (mCurrentTab == i) {
							findViewById(mTabs.get(i).indicatorResId).setSelected(true);
						} else {
							findViewById(mTabs.get(i).indicatorResId).setSelected(false);
						}
					}
				}
			}
		} else {
			if (mTabHostListener != null) {
                mTabHostListener.onTabClick(i, tag);
			}
		}
	}

	public FragmentTransaction doTabChanged(String tabId, FragmentTransaction ft) {
		TabInfo newTab = null;
		for (int i = 0; i < mTabs.size(); i++) {
			TabInfo tab = mTabs.get(i);
			if (tab.tag.equals(tabId)) {
				newTab = tab;
			}
		}
		if (newTab == null) {
			throw new IllegalStateException("No tab known for tag " + tabId);
		}
		if (mLastTab != newTab) {
			if (ft == null) {
				ft = mFragmentManager.beginTransaction();
			}
			if (mLastTab != null) {
				if (mLastTab.fragment != null) {
					ft.hide(mLastTab.fragment);
				}
			}
			if (newTab != null) {
				if (newTab.fragment == null) {
					newTab.fragment = Fragment.instantiate(mContext, newTab.clss.getName(), newTab.args);
                    //((BaseFragment)newTab.fragment).setBackOp(null);
					ft.add(mContainerId, newTab.fragment, newTab.tag);
				} else {
					ft.show(newTab.fragment);
				}
			}
			mLastTab = newTab;
		}
		return ft;
	}

	public String getCurrentTabTag() {
        if (mCurrentTab >= 0 && mCurrentTab < mTabs.size()) {
			return mTabs.get(mCurrentTab).tag;
		}
		return null;
	}

	public TabInfo getCurrentTab() {
		return mLastTab;
	}

	public TabHostListener getTabHostListener() {
		return mTabHostListener;
	}

	public void setTabHostListener(TabHostListener tabHostListener) {
		this.mTabHostListener = tabHostListener;
	}

}
