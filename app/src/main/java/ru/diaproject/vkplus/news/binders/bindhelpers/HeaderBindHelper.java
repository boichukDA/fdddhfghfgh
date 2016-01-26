package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.news.viewholders.base.DataMainViewHolder;

public class HeaderBindHelper {
    private Context context;

    public HeaderBindHelper(Context context){
        this.context = context;
    }

    public void setHeader(Spannable strName, String avatarUrl, Integer intDate, DataMainViewHolder holder){
        Glide.with(context).load(avatarUrl).into(holder.avatar);

        holder.name.setText(strName);
        holder.date.setTextColor(
                ColorUtils.setColorAlpha(
                        ContextCompat.getColor(context, R.color.md_black_1000), ColorUtils.OPACITY_55));

        holder.date.setText(DateUtils.newsDateFormat(intDate, context));
    }
}
