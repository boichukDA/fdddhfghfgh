package ru.diaproject.vkplus.news.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.core.utils.DateUtils;

public class NewsHeaderView extends LinearLayout {
    @Bind(R.id.name)
    public RobotoTextView name;

    @Bind(R.id.date)
    public RobotoTextView date;

    @Bind(R.id.avatar)
    public CircularImageView avatar;


    public NewsHeaderView(Context context) {
        super(context);
        init();
    }

    public NewsHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.news_item_header_layout, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    public void setData(String nameText, String avatarUrl, Integer intDate, Byte sex) {
        switch (sex) {
            case 0:
                Glide.with(getContext())
                        .load(avatarUrl)
                        .into(avatar);
                break;
            case 1:

                Glide.with(getContext())
                        .load(avatarUrl)
                        .into(avatar);
                break;
            default:

                Glide.with(getContext())
                        .load(avatarUrl)
                        .into(avatar);
                break;
        }

        final int color = ColorUtils.setColorAlpha(
                ContextCompat.getColor(getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55);

        final String dateText = DateUtils.newsDateFormat(intDate, getContext());
        name.setText(nameText);
        date.setTextColor(color);
        if (intDate != null) {
            date.setVisibility(VISIBLE);
            date.setText(dateText);
        } else
            date.setVisibility(GONE);
    }

    public void clear() {
        Glide.clear(avatar);
    }
}
