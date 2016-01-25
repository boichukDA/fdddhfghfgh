package ru.diaproject.vkplus.news.binders.bindhelpers;


import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.List;

import ru.diaproject.vkplus.news.NewsUserConfig;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.viewholders.items.CopyHistoryViewHolder;

public class CopyHistoryBindHelper {
    private int maxPhotoCount;

    private final HeaderBindHelper headerBindHelper;
    private final PhotoBindHelper photoBindHelper;
    private final VideoBindHelper videoBindHelper;
    private final AudioBindHelper audioBindHelper;

    public CopyHistoryBindHelper(Context context, int totalPixelsOffset, int copyHistoryOffset, float density, int maxPhotoCount, int maxAudioCount, NewsUserConfig config){
        this.maxPhotoCount = maxPhotoCount;

        headerBindHelper = new HeaderBindHelper(context);

        int photoContainerWidth = (int) (context.getResources().getDisplayMetrics().widthPixels - (copyHistoryOffset + totalPixelsOffset) * density);
        photoBindHelper = new PhotoBindHelper(context, photoContainerWidth, totalPixelsOffset);
        videoBindHelper = new VideoBindHelper(context);
        audioBindHelper = new AudioBindHelper(context, maxAudioCount, config);
    }

    public void setData (CopyHistoryInfo firstHistory, CopyHistoryViewHolder holder, SparseBooleanArray mCollapsedStatus, int position){
        holder.itemView.setVisibility(View.VISIBLE);
        IDataOwner firstOwner = firstHistory.getItemOwner();
        headerBindHelper.setHeader(firstOwner.getFullName(), firstOwner.getPhoto100(),
                firstHistory.getDate(), holder);

        if (firstHistory.getText() == null || "".equals(firstHistory.getText()))
            holder.firstCopyTextView.setVisibility(View.GONE);
        else {
            holder.firstCopyTextView.setText(firstHistory.getText(), mCollapsedStatus, position);
            holder.firstCopyTextView.setVisibility(View.VISIBLE);
        }

        Photos firstPhotos = firstHistory.getAttachmentPhotos();

        if (firstPhotos!=null){
            holder.photoContainer.setVisibility(View.VISIBLE);
            photoBindHelper.setPhotos(firstPhotos, holder);
        }else
            holder.photoContainer.setVisibility(View.GONE);

        List<VideoInfo> videos = firstHistory.getAttachmentVideos();
        if(!(videos == null || videos.size() == 0))
            videoBindHelper.setData(videos, holder.mainVideosholder, maxPhotoCount);
        else
            videoBindHelper.hideLayout(holder.mainVideosholder);

        List<AudioInfo> audios = firstHistory.getAttachmentAudios();
        if(!(audios == null || audios.size() == 0))
            audioBindHelper.setData(audios, holder.mainAudioViewHolder);
        else
            audioBindHelper.hideLayout(holder.mainAudioViewHolder);
    }
    public void hideLayout( CopyHistoryViewHolder holder){
        holder.itemView.setVisibility(View.GONE);
    }
}
