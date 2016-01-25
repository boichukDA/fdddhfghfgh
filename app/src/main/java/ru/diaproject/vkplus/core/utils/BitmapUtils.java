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

import java.util.concurrent.ExecutionException;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.imageloading.ImageLoader;


public class BitmapUtils {
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
              bitmap = ImageLoader.loadBitmap(url, R.drawable.picture_placeholder, R.drawable.picture_placeholder);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
