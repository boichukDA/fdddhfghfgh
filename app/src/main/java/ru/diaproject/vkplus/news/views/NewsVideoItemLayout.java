package ru.diaproject.vkplus.news.views;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.core.view.VideoFrameLayout;
import ru.diaproject.vkplus.core.view.VideoImageView;

public class NewsVideoItemLayout extends VideoFrameLayout {
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
    }

    public void setData(String url, Integer duration) {
        loadVideoData(imageView, textDuration, url, duration);
    }

    public void clear() {
        Glide.clear(imageView);
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
                                .placeholder(R.drawable.picture_placeholder)
                                .error(R.drawable.picture_placeholder)
                                .into(view);
                        textView.setText(text);
                    }
                });
            }
        });
    }
}