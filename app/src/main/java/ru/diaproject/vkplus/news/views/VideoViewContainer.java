package ru.diaproject.vkplus.news.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;


public class VideoViewContainer extends LinearLayout {

    @Bind(R.id.news_main_video_item)
    NewsVideoItemLayout main;

    @Bind(R.id.news_second_video_item)
    NewsVideoItemLayout mainSecond;

    @Bind(R.id.news_video_additional_layout)
    LinearLayout additionalLayout;

    @Bind(R.id.news_video_item_first)
    NewsVideoItemLayout first;

    @Bind(R.id.news_video_item_second)
    NewsVideoItemLayout second;

    @Bind(R.id.news_video_item_third)
    NewsVideoItemLayout third;

    @Bind(R.id.news_video_item_fourth)
    NewsVideoItemLayout fourth;

    public VideoViewContainer(Context context) {
        super(context);
        init();
    }

    public VideoViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.news_videos_layout, this, true);
        ButterKnife.bind(this);
    }

    public void setData(List<VideoInfo> videos){
        main.setVisibility(VISIBLE);
        setDataToMainView(main, videos.get(0));

        if (videos.size() == 1) {
            additionalLayout.setVisibility(GONE);
            mainSecond.setVisibility(GONE);
        }else if (videos.size() == 2){
            mainSecond.setVisibility(VISIBLE);
            additionalLayout.setVisibility(GONE);
            first.setVisibility(GONE);
            second.setVisibility(GONE);
            third.setVisibility(GONE);
            fourth.setVisibility(GONE);
        setDataToMainView(mainSecond, videos.get(1));
        }else if (videos.size() == 3){
            mainSecond.setVisibility(GONE);
            additionalLayout.setVisibility(VISIBLE);
            first.setVisibility(VISIBLE);
            second.setVisibility(GONE);
            third.setVisibility(GONE);
            fourth.setVisibility(VISIBLE);

            setDataToInnerView(first, videos.get(1));
            setDataToInnerView(fourth, videos.get(2));
        }else if (videos.size()==4){
            mainSecond.setVisibility(GONE);
            additionalLayout.setVisibility(VISIBLE);
            first.setVisibility(VISIBLE);
            second.setVisibility(VISIBLE);
            third.setVisibility(GONE);
            fourth.setVisibility(VISIBLE);

            setDataToInnerView(first, videos.get(1));
            setDataToInnerView(second, videos.get(2));
            setDataToInnerView(fourth, videos.get(3));
        }else if (videos.size()>=5){
            mainSecond.setVisibility(GONE);
            additionalLayout.setVisibility(VISIBLE);
            first.setVisibility(VISIBLE);
            second.setVisibility(VISIBLE);
            third.setVisibility(VISIBLE);
            fourth.setVisibility(VISIBLE);

            setDataToInnerView(first, videos.get(1));
            setDataToInnerView(second, videos.get(2));
            setDataToInnerView(third, videos.get(3));
            setDataToInnerView(fourth, videos.get(4));
        }
    }

    public void clear() {
        if (main!=null)
            main.clear();

        if (main!=null)
            mainSecond.clear();

        if (first!=null)
            first.clear();

        if (second!=null)
            second.clear();

        if (third!=null)
            third.clear();

        if (fourth!=null)
            fourth.clear();
    }

    private void setDataToMainView (NewsVideoItemLayout view, VideoInfo info){
        String previewUrl = "".equals(info.getPhoto640())?info.getPhoto320():info.getPhoto640();
        view.setData(previewUrl, info.getDuration());
    }

    private void setDataToInnerView(NewsVideoItemLayout view, VideoInfo info){
        String previewUrl = info.getPhoto320();
        view.setData(previewUrl, info.getDuration());
    }

}
