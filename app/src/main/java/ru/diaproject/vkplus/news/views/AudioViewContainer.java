package ru.diaproject.vkplus.news.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;

public class AudioViewContainer extends LinearLayout{

    public AudioViewContainer(Context context) {
        super(context);
    }

    public AudioViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void clear() {
        removeAllViews();
    }

    public void setData(final List<AudioInfo> audios){
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        removeAllViews();
                    }
                });

                final List<NewsAudioItemLayout> layouts = new ArrayList<>();

                for (AudioInfo item: audios){
                    NewsAudioItemLayout newLayout = new NewsAudioItemLayout(getContext());
                    newLayout.setData(item.getArtist(), item.getTitle(), item.getDuration());
                    layouts.add(newLayout);
                }

                /*((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (NewsAudioItemLayout layout:layouts)
                            addView(layout);
                    }
                });*/
                post(new Runnable() {
                    @Override
                    public void run() {
                        for (NewsAudioItemLayout layout:layouts)
                            addView(layout);
                    }
                });
            }
        });

    }
}
