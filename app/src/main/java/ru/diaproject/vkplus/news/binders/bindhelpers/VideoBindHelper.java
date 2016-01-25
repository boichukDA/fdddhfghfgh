package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.view.View;

import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.viewholders.items.DataVideosViewHolder;

public class VideoBindHelper {
    private final VideoItemBindHelper videoItemBindHelper;
    private Context context;

    public VideoBindHelper(Context context){
        this.context = context;
        videoItemBindHelper = new VideoItemBindHelper(context);
    }
    public void setData(List<VideoInfo> videos, DataVideosViewHolder holder, int maxDisplayCount){
        holder.mainLayout.setVisibility(View.VISIBLE);
        int videosCount = videos.size();

        if (videosCount == 1){
            holder.additionalLayout.setVisibility(View.GONE);
            videoItemBindHelper.setData(videos.get(0), holder.main);
        }else if (videosCount == 2){
            videoItemBindHelper.hideLayout(holder.main);
            holder.additionalLayout.setVisibility(View.VISIBLE);
            videoItemBindHelper.setData(videos.get(0), holder.first);
            videoItemBindHelper.setData(videos.get(1), holder.second);
            videoItemBindHelper.hideLayout(holder.third);
        } else if (videosCount == 3){
            videoItemBindHelper.setData(videos.get(0), holder.main);
            holder.additionalLayout.setVisibility(View.VISIBLE);
            videoItemBindHelper.setData(videos.get(1), holder.first);
            videoItemBindHelper.setData(videos.get(2), holder.second);
            videoItemBindHelper.hideLayout(holder.third);
        }else {
            videoItemBindHelper.setData(videos.get(0), holder.main);
            holder.additionalLayout.setVisibility(View.VISIBLE);
            videoItemBindHelper.setData(videos.get(1), holder.first);
            videoItemBindHelper.setData(videos.get(2), holder.second);
            videoItemBindHelper.setData(videos.get(3), holder.third);
        }

        if (videos.size()> maxDisplayCount) {
            holder.videoCount.setVisibility(View.VISIBLE);

            holder.videoCount.setText(context.getResources().getQuantityString(R.plurals.news_video_count_variants,
                    videosCount - maxDisplayCount, videosCount - maxDisplayCount));
        }
        else holder.videoCount.setVisibility(View.GONE);
    }

    public void hideLayout(DataVideosViewHolder holder){
        holder.mainLayout.setVisibility(View.GONE);
    }
}
