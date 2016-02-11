package ru.diaproject.vkplus.news.binders.bindhelpers;


import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import java.util.List;

import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.model.attachments.audios.AudioInfo;
import ru.diaproject.vkplus.model.attachments.VideoInfo;
import ru.diaproject.vkplus.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.model.newsitems.copyhistory.CopyHistoryInfo;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.news.viewholders.items.CopyHistoryViewHolder;

public class CopyHistoryBindHelper {
    private int maxPhotoCount;

    private final HeaderBindHelper headerBindHelper;
    private final PhotoBindHelper photoBindHelper;
    private final VideoBindHelper videoBindHelper;
    private final AudioBindHelper audioBindHelper;
    private final GifBindHelper gifBindHelper;

    public CopyHistoryBindHelper(Context context, int totalPixelsOffset, int copyHistoryOffset, float density, int maxPhotoCount, int maxAudioCount,int gifMaxCount,  NewsConfiguration config){
        this.maxPhotoCount = maxPhotoCount;

        headerBindHelper = new HeaderBindHelper(context);

        int photoContainerWidth = (int) (context.getResources().getDisplayMetrics().widthPixels - (copyHistoryOffset + totalPixelsOffset) * density);

        gifBindHelper = new GifBindHelper(context, gifMaxCount);
        photoBindHelper = new PhotoBindHelper(context, photoContainerWidth, totalPixelsOffset);
        videoBindHelper = new VideoBindHelper(context);
        audioBindHelper = new AudioBindHelper(context, maxAudioCount, config);
    }

    public void setData (CopyHistoryInfo firstHistory, CopyHistoryViewHolder holder, SparseBooleanArray mCollapsedStatus, int position){
        holder.itemView.setVisibility(View.VISIBLE);
        IDataOwner firstOwner = firstHistory.getItemOwner();
        headerBindHelper.setHeader(firstOwner,
                firstHistory.getDate(), holder);

        if (firstHistory.getText() == null || "".equals(firstHistory.getText()))
            holder.firstCopyTextView.setVisibility(View.GONE);
        else {
            holder.firstCopyTextView.setText(firstHistory.getSpannableText(), mCollapsedStatus, position);
            holder.firstCopyTextView.setVisibility(View.VISIBLE);
        }

        List<DocInfo> docs = firstHistory.getAttachmentDocs();
        if (!(docs == null || docs.size() == 0))
            gifBindHelper.setData(docs, holder.mainGifViewHolder);
        else
            gifBindHelper.hideLayout(holder.mainGifViewHolder);

        Photos firstPhotos = firstHistory.getAttachmentPhotos();

        if (firstPhotos!=null){
            holder.photoContainer.setVisibility(View.VISIBLE);
            photoBindHelper.setPhotos(firstHistory, holder );
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
