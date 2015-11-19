package ru.diaproject.vkplus.news.views;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.util.Util;

import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.core.view.VideoFrameLayout;
import ru.diaproject.vkplus.core.view.VideoImageView;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;

public class NewsVideoItemLayout extends VideoFrameLayout {
    BaseRequestOptions pictureOptions;

    @Bind(R.id.news_video_placeholder)
    VideoImageView imageView;

    @Bind(R.id.news_video_duration)
    TextView textDuration;

    public NewsVideoItemLayout(Context context) {
        super(context);
        init();
    }

    public NewsVideoItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.news_video_item_layout, this, true);
        ButterKnife.bind(this);

        pictureOptions = new BaseRequestOptions() {
        };

        pictureOptions.format(DecodeFormat.PREFER_RGB_565);
        pictureOptions.placeholder(R.drawable.picture_placeholder);
        pictureOptions.error(R.drawable.picture_placeholder);
    }

    public void setData(String url, Integer duration) {
        loadVideoData(imageView, textDuration, url, duration);
    }

    public void clear(NewsPagerCardFragment newsPagerCardFragment) {
        Glide.with(newsPagerCardFragment).clear(imageView);
    }

    public void loadVideoData(final ImageView view, final TextView textView, final String url, final Integer duration) {
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                final String text = DateUtils.toVideoTimeFormat(duration);
                ((Activity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext())
                                .load(url)
                                .apply(pictureOptions)
                                .into(view);
                        textView.setText(text);
                    }
                });
            }
        });
    }
}