package ru.diaproject.vkplus.news.viewholders;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.viewholders.base.DataMainViewHolder;

public class DataPhotosViewHolder extends DataMainViewHolder {
    //photos
    public RelativeLayout photoContainer;
    public RelativeLayout photoSubContainer;

    public ImageView mainImage;
    public ImageView firstImage;
    public ImageView secondImage;
    public ImageView thirdImage;
    public ImageView fourthImage;

    public DataPhotosViewHolder(View itemView) {
        super(itemView);

        //photos
        photoContainer = (RelativeLayout) itemView.findViewById(R.id.news_photos_container);
        photoSubContainer = (RelativeLayout) itemView.findViewById(R.id.news_photos_sub_container);

        mainImage = (ImageView) itemView.findViewById(R.id.news_photos_main_image);
        firstImage = (ImageView) itemView.findViewById(R.id.news_photos_first_image);
        secondImage = (ImageView) itemView.findViewById(R.id.news_photos_second_image);
        thirdImage = (ImageView) itemView.findViewById(R.id.news_photos_third_image);
        fourthImage = (ImageView) itemView.findViewById(R.id.news_photos_fourth_image);
    }

    @Override
    public void clear() {
        super.clear();
        if (mainImage!=null)
            Glide.clear(mainImage);

        if (firstImage!=null)
            Glide.clear(firstImage);

        if (secondImage!=null)
            Glide.clear(secondImage);

        if (secondImage!=null)
            Glide.clear(thirdImage);

        if (secondImage!=null)
            Glide.clear(fourthImage);
    }
}
