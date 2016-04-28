package com.example.clam314.changeavatar;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by clam314 on 2016/4/28.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }
}
