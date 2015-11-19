package ru.diaproject.vkplus.news.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.vkcore.user.VKUser;


public class NewsPostAttachmentContainer extends LinearLayout {

    @Bind(R.id.news_post_photo)
    public PhotoViewContainer photoViewContainer;

    @Bind(R.id.news_post_video)
    public VideoViewContainer videoViewContainer;

    @Bind(R.id.news_post_audio)
    public AudioViewContainer audioViewContainer;

    BaseRequestOptions pictureOptions;

    public NewsPostAttachmentContainer(Context context) {
        super(context);
        init();
    }

    public NewsPostAttachmentContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.news_post_content_item_layout, (ViewGroup) getRootView(), false);
        ButterKnife.bind(this, view);

        pictureOptions = new BaseRequestOptions() {
        };

        pictureOptions.format(DecodeFormat.PREFER_RGB_565);
        pictureOptions.placeholder(R.drawable.picture_placeholder);
        pictureOptions.error(R.drawable.picture_placeholder);

        addView(view);
    }

    public void setPhotos(Photos photos,Integer ownerId, Integer date, VKUser user) {
        if (photos.getPhotos().size() == 0)
            photoViewContainer.setVisibility(GONE);
        else{
            photoViewContainer.setVisibility(VISIBLE);
            photoViewContainer.setData(photos, ownerId, date, user);
        }
    }

    public void clear(NewsPagerCardFragment newsPagerCardFragment) {
        if (photoViewContainer!=null)
            photoViewContainer.clear(newsPagerCardFragment);

        if (videoViewContainer!=null)
            videoViewContainer.clear(newsPagerCardFragment);

        if (audioViewContainer!=null)
            audioViewContainer.clear(newsPagerCardFragment);
    }

    public void setVideos(List<VideoInfo> videos) {
        videoViewContainer.setVisibility(VISIBLE);
        videoViewContainer.setData(videos);
    }

    public void setAudios(List<AudioInfo> audios) {
        audioViewContainer.setVisibility(VISIBLE);
        audioViewContainer.setData(audios);
    }

    public void hideVideoLayout(){
        videoViewContainer.setVisibility(GONE);
    }

    public void hideAudioLayout() {
       audioViewContainer.setVisibility(GONE);
    }
}
