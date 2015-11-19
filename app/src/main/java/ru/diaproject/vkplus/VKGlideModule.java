package ru.diaproject.vkplus;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

public class VKGlideModule implements GlideModule {
    final int CACHE_BITMAP_SIZE = (int)(Runtime.getRuntime().maxMemory() / 8192f);
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(CACHE_BITMAP_SIZE));
        builder.setBitmapPool(new LruBitmapPool(CACHE_BITMAP_SIZE));
    }

    @Override
    public void registerComponents(Context context, Registry registry) {

    }
}
