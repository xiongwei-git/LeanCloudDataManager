package com.ted.lcmanager.app.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ted.lcmanager.app.Constants;
import com.ted.lcmanager.app.R;
import com.ted.lcmanager.app.model.*;
import com.ted.lcmanager.app.network.LeanCloudNorClient;
import com.ted.lcmanager.app.network.MyJsonRequest;
import com.ted.lcmanager.app.network.VolleyClient;
import com.ted.lcmanager.app.util.Utils;
import org.androidannotations.annotations.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ted on 2015/4/5.
 */
@EFragment(R.layout.fragment_fetch_data_layout)
public class FetchDataFragment extends Fragment {
    @ViewById(R.id.server_data_count)
    TextView mServerCount;
    @ViewById(R.id.my_data_count)
    TextView mMyCount;
    @ViewById(R.id.update_all_btn)
    Button mUpdateNewBtn;
    @ViewById(R.id.update_info_btn)
    Button mUpdateInfoBtn;
    @ViewById(R.id.add_521_btn)
    Button mAddSpecialDataBtn;
    @ViewById(R.id.get_my_10_btn)
    Button mGet10Btn;
    @ViewById(R.id.update_10_btn)
    Button mUpdate10Btn;

    private LeanCloudNorClient mLeanCloudNorClient;
    private VolleyClient mVolleyClient;

    private List<StealDataModel> mAllData;

    private PictureInfo mPictureInfo;

    private ArrayList<AVObject> mLastNew10Data;

    @AfterViews
    void AfterViews(){
        mUpdate10Btn.setEnabled(false);
        mUpdateNewBtn.setEnabled(false);
        mUpdateInfoBtn.setEnabled(false);
        mAddSpecialDataBtn.setEnabled(false);
        mGet10Btn.setEnabled(false);
        mLeanCloudNorClient = new LeanCloudNorClient();
        mVolleyClient = new VolleyClient(getActivity());

    }

    @Click(R.id.get_server_data_btn)
    void getServerData() {
        JsonObjectRequest getServerData = new JsonObjectRequest(Constants.STEAL_DATA_IP, null,
                mGetServerDataListener, mCommonErrorListener);
        mVolleyClient.sendRequest(getServerData);
    }

    @Click(R.id.get_my_data_btn)
    void getMyData(){
        MyJsonRequest getMyDataRequest = new MyJsonRequest(Constants.SERVER_IP_PRE+Constants.PICTURE_INFO, null,
                mGetMyDataListener, mCommonErrorListener);
        mVolleyClient.sendRequest(getMyDataRequest);
    }

    @Click(R.id.get_my_10_btn)
    void getMy10Data(){
        mLeanCloudNorClient.getMy10NewData(mGetMy10DataCallback);
    }

    @Click(R.id.update_10_btn)
    void updateNew10Data(){
        if(null == mAllData || mAllData.size()==0)return;
        if(null == mLastNew10Data || mLastNew10Data.size()==0 || mLastNew10Data.size() < 10)return;
        for (int i = 0;i< 10;i++){
            StealDataModel model = mAllData.get(i);
            mLeanCloudNorClient.updateNewData(model,mLastNew10Data.get(i));
        }
    }

    @Click(R.id.update_all_btn)
    void updateNewData(){
        if(null == mAllData || mAllData.size()==0)return;
        if(null == mPictureInfo || mPictureInfo.getAll() > mAllData.size())return;
        for (int i = 0;i< mAllData.size();i++){
            StealDataModel model = mAllData.get(i);
            if(model.getId() > mPictureInfo.getAll()){
                mLeanCloudNorClient.addStealData(model, i);
            }
        }
    }

//    @Click(R.id.update_all_btn)
//    void updateAllData(){
//        if(null == mAllData || mAllData.size()==0)return;
//        for (int i = 0;i< mAllData.size();i++){
//            StealDataModel model = mAllData.get(i);
//            mLeanCloudNorClient.addStealData(model,i);
//        }
//    }

    @Click(R.id.update_info_btn)
    void updateInfoData() {
        if (null == mAllData || mAllData.size() == 0) return;
        StatisticsModel statisticsModel = new StatisticsModel();
        for (int i = 0; i < mAllData.size(); i++) {
            StealDataModel model = mAllData.get(i);
            statisticsModel.Statistics(model);
        }
        //add special one
        StealDataModel model = new StealDataModel();
        model.setCategory(4);
        statisticsModel.Statistics(model);
        mLeanCloudNorClient.addPictureCount(statisticsModel,mPictureInfo.getObjectId());
    }

    @Click(R.id.add_521_btn)
    void add521Data(){
        if(null == mAllData || mAllData.size()==0)return;
        StealDataModel model = mAllData.get(1999);
        mLeanCloudNorClient.add521lData(model,521);
    }

    @UiThread
    void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    /***************************************************/


    private Response.ErrorListener mCommonErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("TAG", error.getMessage(), error);
            showToast("服务异常"+error.getMessage());
        }
    };


    private Response.Listener<JSONObject> mGetServerDataListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            StealDataResult result = gson.fromJson(response.toString(), StealDataResult.class);
            mAllData = result.getData();
            System.out.println("-------------------------------------");
            String resultStr = "一共获取数据" + mAllData.size() + "条";
            System.out.println(resultStr);
            showToast(resultStr);
            mServerCount.setText(resultStr);
        }
    };

    private Response.Listener<JSONObject> mGetMyDataListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'").create();
            PictureInfoResult result = gson.fromJson(response.toString(), PictureInfoResult.class);
            mPictureInfo = result.getResults().get(0);
            System.out.println("-------------------------------------");
            String resultStr = "我的服务器数据"+mPictureInfo.getAll()+"条,更新时间为"+ Utils.getFormatDateStr(mPictureInfo.getUpdatedAt());
            System.out.println(resultStr);
            showToast(resultStr);
            mMyCount.setText(resultStr);
            if(null != mAllData && mPictureInfo.getAll() < mAllData.size()){
                mGet10Btn.setEnabled(true);
                mUpdateNewBtn.setEnabled(true);
                mUpdateInfoBtn.setEnabled(true);
                mAddSpecialDataBtn.setEnabled(false);
            }else {
                mGet10Btn.setEnabled(false);
                mUpdateNewBtn.setEnabled(false);
                mUpdateInfoBtn.setEnabled(false);
                mAddSpecialDataBtn.setEnabled(false);
            }
        }
    };

    private FindCallback<AVObject> mGetMy10DataCallback = new FindCallback<AVObject>() {
        @Override
        public void done(List<AVObject> avObjects, AVException e) {
            if (e == null) {
                if(null == mLastNew10Data)mLastNew10Data = new ArrayList<AVObject>();
                mLastNew10Data.clear();
                mLastNew10Data.addAll(avObjects);
                Log.d("成功", "查询到" + avObjects.size() + " 条符合条件的数据");
                mUpdate10Btn.setEnabled(true);
            } else {
                Log.d("失败", "查询错误: " + e.getMessage());
            }
        }
    };
}
