package ru.diaproject.vkplus.core.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.model.attachments.photos.PhotosInfo;

public class LoadUtils {
    public static void loadBitmapIntoBigView(ImageView view, PhotosInfo info,  int reqWidth, int reqHeight){
        int bWidth = info.getWidth();
        int bHeight = info.getHeight();

        int nWidth = bWidth;
        int nHeight = bHeight;

        float coeff;
        if (nWidth > nHeight){
            coeff = (float)nWidth/nHeight;
            nHeight = reqHeight;
            nWidth = (int) (nHeight*coeff);
        }else {
            coeff = (float)nHeight/nWidth;
            nWidth = reqWidth;
            nHeight = (int) (nWidth*coeff);
        }

        Glide.with(view.getContext()).load(info.getPhoto604()).override(nWidth, nHeight).into(view);
    }

    public static void loadBitmapIntoLittleView(ImageView view, PhotosInfo info) {
        Glide.with(view.getContext()).load(info.getPhoto130()).into(view);
    }
}
