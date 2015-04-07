package com.ted.lcmanager.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ted.lcmanager.app.R;

/**
 * Created by Ted on 2015/4/5.
 */
public class SecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_layout,null);
        return view;
    }
}
