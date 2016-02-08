package ru.diaproject.vkplus.news.binders.bindhelpers;

import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.viewholders.items.DataAudioItemHolder;

public class AudioItemBindHelper {
    private NewsConfiguration newsUserConfiguration;
    public AudioItemBindHelper(NewsConfiguration newsUserConfiguration) {
        this.newsUserConfiguration = newsUserConfiguration;
    }

    public void setData(AudioInfo audioInfo, DataAudioItemHolder holder) {
        holder.author.setText(audioInfo.getArtist());
        holder.song.setText(audioInfo.getTitle());
        String durationString =audioInfo.getConvertedDuration();
        holder.duration.setText(durationString);
        holder.icon.setImageBitmap(newsUserConfiguration.getAudioPlayBitmap());
    }
}
