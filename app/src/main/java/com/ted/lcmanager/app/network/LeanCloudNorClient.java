package com.ted.lcmanager.app.network;

import android.util.Log;
import com.avos.avoscloud.*;
import com.ted.lcmanager.app.Constants;
import com.ted.lcmanager.app.model.StatisticsModel;
import com.ted.lcmanager.app.model.StealDataModel;

import java.util.List;


/**
 * Created by Ted on 2015/3/29.
 */
public class LeanCloudNorClient {

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

    public void addPictureCount(final StatisticsModel model){
        if(null == model)return;
        AVObject testObject = new AVObject(Constants.PICTURE_INFO);
        testObject.put("all",model.getAll());
        testObject.put("featured", model.getSpe());
        testObject.put("other", model.getOTH());
        testObject.put("building", model.getBui());
        testObject.put("food", model.getFoo());
        testObject.put("nature", model.getFau());
        testObject.put("people", model.getPeo());
        testObject.put("technology", model.getTec());
        testObject.put("object", model.getObj());
        testObject.saveInBackground(new SaveCallback() {
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
