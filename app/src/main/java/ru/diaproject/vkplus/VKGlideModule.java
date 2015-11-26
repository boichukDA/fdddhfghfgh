package ru.diaproject.vkplus;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import ru.diaproject.vkplus.glide.OkHttpClientFactory;

public class VKGlideModule implements GlideModule {
    final int CACHE_BITMAP_SIZE = (int)(Runtime.getRuntime().maxMemory() / 8192f);
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(CACHE_BITMAP_SIZE));
        builder.setBitmapPool(new LruBitmapPool(CACHE_BITMAP_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }

}
