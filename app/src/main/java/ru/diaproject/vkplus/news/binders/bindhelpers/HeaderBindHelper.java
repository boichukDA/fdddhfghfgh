package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.text.Spannable;

import com.bumptech.glide.Glide;

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

        holder.date.setText(DateUtils.newsDateFormat(intDate, context));
    }
}
