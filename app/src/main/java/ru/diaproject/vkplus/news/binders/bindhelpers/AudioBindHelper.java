package ru.diaproject.vkplus.news.binders.bindhelpers;


import android.content.Context;
import android.view.View;

import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.viewholders.items.DataAudiosViewHolder;

public class AudioBindHelper {
    private final AudioItemBindHelper audioItemBindHelper;

    private Context context;
    private int maxAudiosCount;

    public AudioBindHelper(Context context, int maxAudioCount, NewsConfiguration newsUserConfiguraiotn){
        this.context = context;
        this.maxAudiosCount = maxAudioCount;

        audioItemBindHelper = new AudioItemBindHelper(newsUserConfiguraiotn);
    }

    public void setData(List<AudioInfo> audios, DataAudiosViewHolder holder){
        holder.mainLayout.setVisibility(View.VISIBLE);
        int size = audios.size();
        if (size == 1){
            audioItemBindHelper.setData(audios.get(0), holder.first);
            holder.second.hideLayout();
            holder.third.hideLayout();
            holder.fourth.hideLayout();
            holder.fifth.hideLayout();
        }else if (size == 2){
            audioItemBindHelper.setData(audios.get(0), holder.first);
            audioItemBindHelper.setData(audios.get(1), holder.second);
            holder.third.hideLayout();
            holder.fourth.hideLayout();
            holder.fifth.hideLayout();
        } else if (size == 3){
            audioItemBindHelper.setData(audios.get(0), holder.first);
            audioItemBindHelper.setData(audios.get(1), holder.second);
            audioItemBindHelper.setData(audios.get(2), holder.third);
            holder.fourth.hideLayout();
            holder.fifth.hideLayout();
        }else if (size == 4){
            audioItemBindHelper.setData(audios.get(0), holder.first);
            audioItemBindHelper.setData(audios.get(1), holder.second);
            audioItemBindHelper.setData(audios.get(2), holder.third);
            audioItemBindHelper.setData(audios.get(3), holder.fourth);
            holder.fifth.hideLayout();
        }else if (size >= 5){
            audioItemBindHelper.setData(audios.get(0), holder.first);
            audioItemBindHelper.setData(audios.get(1), holder.second);
            audioItemBindHelper.setData(audios.get(2), holder.third);
            audioItemBindHelper.setData(audios.get(3), holder.fourth);
            audioItemBindHelper.setData(audios.get(3), holder.fifth);
        }

        if (audios.size()> maxAudiosCount) {
            holder.audioCount.setVisibility(View.VISIBLE);

            holder.audioCount.setText(context.getResources().getQuantityString(R.plurals.news_video_count_variants,
                    size - maxAudiosCount, size - maxAudiosCount));
        }
        else holder.audioCount.setVisibility(View.GONE);
    }

    public void hideLayout(DataAudiosViewHolder holder){
        holder.mainLayout.setVisibility(View.GONE);
    }
}
