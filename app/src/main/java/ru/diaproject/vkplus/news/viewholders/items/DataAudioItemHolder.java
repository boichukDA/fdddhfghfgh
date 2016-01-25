package ru.diaproject.vkplus.news.viewholders.items;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;

public class DataAudioItemHolder extends RecyclerView.ViewHolder{
    public CircularImageView icon;
    public TextView author;
    public TextView song;
    public TextView duration;

    public DataAudioItemHolder(View itemView) {
        super(itemView);

        icon = (CircularImageView) itemView.findViewById(R.id.news_post_audio_icon);
        author = (TextView) itemView.findViewById(R.id.news_post_audio_author);
        song = (TextView) itemView.findViewById(R.id.news_post_audio_song);
        duration = (TextView) itemView.findViewById(R.id.news_post_audio_duration);
    }

    public void clear(){
        Glide.clear(icon);
    }

    public void hideLayout(){
        itemView.setVisibility(View.GONE);
    }
}
