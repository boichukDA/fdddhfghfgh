package ru.diaproject.vkplus.imageloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import ru.diaproject.vkplus.VKPlusApplication;

public abstract class ImageLoader {

    public static ILoadManager with(Context context){
        return new LoadManager(context);
    }


    public static ILoadManager with(Fragment parent) {
        return new LoadManager(parent.getContext());
    }

    public static void clear(ImageView view){
        /*Glide.get(VKPlusApplication.getStaticContext()).clearMemory();
        Glide.clear(view);*/

    }

    public static Bitmap loadBitmap(String url, int placeholder, int error) throws ExecutionException, InterruptedException {
       return  Glide.with(VKPlusApplication.getStaticContext()).load(url).asBitmap()
                .error(error)
                .placeholder(placeholder)
                .format(DecodeFormat.PREFER_RGB_565).into(-1, -1).get();
    }

    public static Bitmap loadBitmap(String url) throws ExecutionException, InterruptedException {
        return  Glide.with(VKPlusApplication.getStaticContext()).load(url).asBitmap()
                .format(DecodeFormat.PREFER_RGB_565).into(-1, -1).get();
    }

    public static void load( ImageView view, final String url, final OnCompletedListener listener){
        Glide.with(VKPlusApplication.getStaticContext()).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                listener.onCompleted();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                listener.onCompleted();
                return false;
            }
        }).into(view);
    }
}
