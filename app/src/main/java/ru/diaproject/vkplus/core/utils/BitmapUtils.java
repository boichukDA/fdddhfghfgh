package ru.diaproject.vkplus.core.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;

import java.util.concurrent.ExecutionException;

import ru.diaproject.vkplus.R;


public class BitmapUtils {
    private static BaseRequestOptions pictureOptions;
    static{
        pictureOptions = new BaseRequestOptions() {
        };

        pictureOptions.format(DecodeFormat.PREFER_RGB_565);
        pictureOptions.placeholder(R.drawable.picture_placeholder);
        pictureOptions.error(R.drawable.picture_placeholder);
    }

    public static Bitmap appyColorFilterForResource(Context context, int resourceId,@ColorRes int color,  PorterDuff.Mode mode ){
        Bitmap immutable = BitmapFactory.decodeResource(context.getResources(), resourceId);
        final Bitmap mutable = immutable.copy(Bitmap.Config.ARGB_8888, true);
        Canvas c = new Canvas(mutable);
        Paint p = new Paint();
        p.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(context, color), mode));
        c.drawBitmap(mutable, 0.f, 0.f, p);
        return mutable;
    }

    public static Bitmap loadBitmap(String url, Context context){
        Bitmap bitmap = null;
        try {
              bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(pictureOptions).submit().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
