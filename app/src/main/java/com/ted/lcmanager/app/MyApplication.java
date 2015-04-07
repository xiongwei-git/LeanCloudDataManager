package com.ted.lcmanager.app;

import android.app.Application;
import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Ted on 2015/3/26.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, Constants.SERVER_ID, Constants.SERVER_KEY);
    }
}
