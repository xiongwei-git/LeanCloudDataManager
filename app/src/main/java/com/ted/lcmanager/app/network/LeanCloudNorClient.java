package com.ted.lcmanager.app.network;

import android.text.TextUtils;
import android.util.Log;
import com.avos.avoscloud.*;
import com.ted.lcmanager.app.Constants;
import com.ted.lcmanager.app.model.StatisticsModel;
import com.ted.lcmanager.app.model.StealDataModel;
import com.ted.lcmanager.app.util.Utils;

import java.util.List;


/**
 * Created by Ted on 2015/3/29.
 */
public class LeanCloudNorClient {
    public void transDataToQiNiu(AVObject avObject){
        if(null == avObject)return;
        avObject.put("server_type","qiniu");
        String oldSrc = avObject.getString("image_src");
        if(TextUtils.isEmpty(oldSrc))return;
        avObject.put("image_src", Utils.getQiNiuUrlFromImgix(oldSrc));
        avObject.saveInBackground(new SaveCallback(){
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.e("xiongwei", "更新七牛数据之一成功");
                } else {
                    Log.e("xiongwei", "更新七牛数据之一失败");
                }
            }
        });
    }


    public void updateNewData(final StealDataModel model,AVObject avObject){
        if(null == model)return;
        if(null == avObject)return;
        avObject.put("server_type","imgix");
        avObject.put("position", model.getId());
        avObject.put("author", model.getAuthor());
        avObject.put("image_src", model.getImage_src());
        avObject.put("color", model.getColor());
        avObject.put("date", model.getDate());
        avObject.put("modified_date", model.getModified_date());
        avObject.put("ratio", model.getRatio());
        avObject.put("width", model.getWidth());
        avObject.put("height", model.getHeight());
        avObject.put("featured", model.getFeatured());
        avObject.put("category", model.getCategory());
        avObject.put("corrupt", model.getCorrupt());
        avObject.saveInBackground(new SaveCallback(){
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.e("xiongwei", "更新10条数据之一成功"+model.getId());
                } else {
                    Log.e("xiongwei", "更新10条数据之一失败"+model.getId());
                }
            }
        });
    }


    public void addNewData(final StealDataModel model,int position){
        if(null == model)return;
        AVObject testObject = new AVObject(Constants.NEW_PICTURE);
        testObject.put("server_type","imgix");
        testObject.put("position", model.getId());
        testObject.put("author", model.getAuthor());
        testObject.put("image_src", model.getImage_src());
        testObject.put("color", model.getColor());
        testObject.put("date", model.getDate());
        testObject.put("modified_date", model.getModified_date());
        testObject.put("ratio", model.getRatio());
        testObject.put("width", model.getWidth());
        testObject.put("height", model.getHeight());
        testObject.put("featured", model.getFeatured());
        testObject.put("category", model.getCategory());
        testObject.put("corrupt", model.getCorrupt());
        testObject.saveInBackground(new MyCallBack(position));
    }


    public void addStealData(final StealDataModel model,int position){
        if(null == model)return;
        AVObject testObject = new AVObject(Constants.ALL_PICTURE);
        testObject.put("server_type","imgix");
        testObject.put("position", model.getId());
        testObject.put("author", model.getAuthor());
        testObject.put("image_src", model.getImage_src());
        testObject.put("color", model.getColor());
        testObject.put("date", model.getDate());
        testObject.put("modified_date", model.getModified_date());
        testObject.put("ratio", model.getRatio());
        testObject.put("width", model.getWidth());
        testObject.put("height", model.getHeight());
        testObject.put("featured", model.getFeatured());
        testObject.put("category", model.getCategory());
        testObject.put("corrupt", model.getCorrupt());
        testObject.saveInBackground(new MyCallBack(position));
    }

    public void add521lData(final StealDataModel model,int position){
        if(null == model)return;
        AVObject testObject = new AVObject(Constants.ALL_PICTURE);
        testObject.put("server_type","imgix");
        testObject.put("position", 521);
        testObject.put("author", model.getAuthor());
        testObject.put("image_src", model.getImage_src());
        testObject.put("color", model.getColor());
        testObject.put("date", model.getDate());
        testObject.put("modified_date", model.getModified_date());
        testObject.put("ratio", model.getRatio());
        testObject.put("width", model.getWidth());
        testObject.put("height", model.getHeight());
        testObject.put("featured", model.getFeatured());
        testObject.put("category", model.getCategory());
        testObject.put("corrupt", model.getCorrupt());
        testObject.saveInBackground(new MyCallBack(position));
    }

    public void addPictureCount(final StatisticsModel model,final String objectId){
        if(null == model || TextUtils.isEmpty(objectId))return;
        AVObject avObject = new AVObject(Constants.PICTURE_INFO);
        avObject.setObjectId(objectId);
        avObject.put("all",model.getAll());
        avObject.put("featured", model.getSpe());
        avObject.put("other", model.getOTH());
        avObject.put("building", model.getBui());
        avObject.put("food", model.getFoo());
        avObject.put("nature", model.getFau());
        avObject.put("people", model.getPeo());
        avObject.put("technology", model.getTec());
        avObject.put("object", model.getObj());
        avObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.e("xiongwei", "更新照片信息成功");
                } else {
                    Log.e("xiongwei", "更新照片信息失败");
                }
            }
        });
    }

    public void getMy10NewData(FindCallback<AVObject> callback){
        AVQuery<AVObject> query = new AVQuery<AVObject>(Constants.NEW_PICTURE);
        query.findInBackground(callback);
    }




    class MyCallBack extends SaveCallback {

        private int position = 0;
        public MyCallBack(int position) {
            super();
            this.position = position;
        }

        @Override
        public void done(AVException e) {
            if (e == null) {
                Log.e("xiongwei", "save success at " + position);
            } else {
                Log.e("xiongwei", "save failed at " + position);
            }
        }
    }
}
