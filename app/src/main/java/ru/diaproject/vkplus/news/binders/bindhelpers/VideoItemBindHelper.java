package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.view.View;


import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.viewholders.items.DataVideoItemHolder;

public class VideoItemBindHelper {
    private Context context;

    public VideoItemBindHelper(Context context){
        this.context = context;
    }

    public void setData(VideoInfo video, DataVideoItemHolder holder){
        holder.itemView.setVisibility(View.VISIBLE);
        holder.textDuration.setText(video.getConvertedDuration());
        Glide.with(context).load(video.getPhoto320()).into(holder.imageView);
    }

    public void hideLayout(DataVideoItemHolder holder){
        holder.itemView.setVisibility(View.GONE);
    }
}
