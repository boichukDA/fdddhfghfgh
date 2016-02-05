package ru.diaproject.vkplus.news.viewholders.items;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.view.VideoImageView;
import ru.diaproject.vkplus.news.viewholders.base.DataItemViewHolder;

public class DataVideoItemHolder extends DataItemViewHolder{
    public VideoImageView imageView;
    public TextView textDuration;

    public DataVideoItemHolder(View itemView) {
        super(itemView);
        imageView = (VideoImageView) itemView.findViewById(R.id.news_video_placeholder);
        textDuration = (TextView) itemView.findViewById(R.id.news_video_duration);
    }

    public void clear() {
        Glide.clear(imageView);
    }

}
