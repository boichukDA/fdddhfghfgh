package ru.diaproject.vkplus.news.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.core.utils.DateUtils;

public class NewsHeaderView extends LinearLayout {
    @Bind(R.id.name)
    public RobotoTextView name;

    @Bind(R.id.date)
    public RobotoTextView date;

    @Bind(R.id.avatar)
    public CircularImageView avatar;

    private BaseRequestOptions opt;

    public NewsHeaderView(Context context) {
        super(context);
        init();
    }

    public NewsHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.news_item_header_layout, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    public void setData(String nameText, String avatarUrl, Integer intDate, Byte sex){
        loadHeaderData(nameText, avatarUrl, intDate, sex);
    }

    public void loadHeaderData( final String nameText, final String avatarUrl, final Integer intDate, final Byte sex){
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                if (opt == null) {
                    opt = new BaseRequestOptions() {
                    };
                    switch(sex){
                        case 0:
                            opt.placeholder(R.drawable.group_silhouette);
                            opt.error(R.drawable.group_silhouette);
                            break;
                        case 1:
                            opt.placeholder(R.drawable.woman_silhouette);
                            opt.error(R.drawable.woman_silhouette);
                            break;
                        default:
                            opt.placeholder(R.drawable.man_siluette);
                            opt.error(R.drawable.man_siluette);
                            break;
                    }
                }

                final int color = ColorUtils.setColorAlpha(
                        ContextCompat.getColor(getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55);

                final String dateText = DateUtils.newsDateFormat(intDate, getContext());

                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(nameText);
                        Glide.with(getContext())
                                .load(avatarUrl)
                                .apply(opt)
                                .into(avatar);
                        date.setTextColor(color );
                        if (intDate!=null) {
                            date.setVisibility(VISIBLE);
                            date.setText(dateText);
                        }
                        else
                            date.setVisibility(GONE);
                    }
                });
            }
        });

    }
}
