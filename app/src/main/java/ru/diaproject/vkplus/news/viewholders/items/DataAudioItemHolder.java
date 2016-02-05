package ru.diaproject.vkplus.news.viewholders.items;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.news.viewholders.base.DataItemViewHolder;

public class DataAudioItemHolder extends DataItemViewHolder{
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

    @Override
    public void applyColorScheme(ColorScheme scheme) {
        super.applyColorScheme(scheme);
        author.setTextColor(scheme.getTextColor());
        song.setTextColor(scheme.getTextColor());
        duration.setTextColor(scheme.getTextColor());
    }
}
