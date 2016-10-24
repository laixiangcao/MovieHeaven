package com.jiumeng.movieheaven2.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by jiumeng on 2016/10/23.
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置最大缓存大小
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,80*1024*1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
