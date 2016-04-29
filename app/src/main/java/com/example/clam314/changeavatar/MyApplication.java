package com.example.clam314.changeavatar;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by clam314 on 2016/4/28.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.getInstance().init(
                new ImageLoaderConfiguration.Builder(this)
                        .memoryCacheExtraOptions(480, 800)
                        .threadPoolSize(4)
                        .threadPriority(Thread.NORM_PRIORITY)
                        .denyCacheImageMultipleSizesInMemory()
                        .memoryCache(new LRULimitedMemoryCache(50 * 1024 * 1024))
                        .memoryCacheSize(50 * 1024 * 1024)
                        .tasksProcessingOrder(QueueProcessingType.LIFO)
                        .diskCacheSize(50 * 1024 * 1024)
                        .diskCacheFileCount(200)
                        .build());
    }
}
