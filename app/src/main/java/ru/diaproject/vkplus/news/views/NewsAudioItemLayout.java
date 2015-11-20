package ru.diaproject.vkplus.news.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.DateUtils;

public class NewsAudioItemLayout extends LinearLayout{
    @Bind(R.id.news_post_audio_icon)
    CircularImageView icon;

    @Bind(R.id.news_post_audio_author)
    TextView author;

    @Bind(R.id.news_post_audio_song)
    TextView song;

    @Bind(R.id.news_post_audio_duration)
    TextView duration;

    public NewsAudioItemLayout(Context context) {
        super(context);
        init();
    }

    public NewsAudioItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.news_post_audio_item, this, true);
        ButterKnife.bind(this);

        setOrientation(HORIZONTAL);
    }

    public void clear() {
    }

    public void setData(String author, String song, Integer duration){
        this.author.setText(author);
        this.song.setText(song);
        String durationString = DateUtils.toVideoTimeFormat(duration);
        this.duration.setText(durationString);

        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap immutable = BitmapFactory.decodeResource(getResources(), R.drawable.news_post_audio_play_large_white);
                final Bitmap mutable = immutable.copy(Bitmap.Config.ARGB_8888, true);
                Canvas c = new Canvas(mutable);
                Paint p = new Paint();
                p.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.m_indigo), PorterDuff.Mode.MULTIPLY));
                c.drawBitmap(mutable, 0.f, 0.f, p);
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        icon.setImageBitmap(mutable);
                    }
                });
            }
        });
    }
}
