package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.view.View;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.news.viewholders.base.DataMainViewHolder;
import ru.diaproject.vkplus.profiles.VKProfileDetailsActivity;

public class HeaderBindHelper {
    private Context context;

    public HeaderBindHelper(Context context){
        this.context = context;
    }

    public void setHeader(Spannable strName, String avatarUrl, Integer intDate, DataMainViewHolder holder){
        Glide.with(context).load(avatarUrl).into(holder.avatar);

        holder.name.setText(strName);

        holder.date.setText(DateUtils.newsDateFormat(intDate, context));

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VKProfileDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
