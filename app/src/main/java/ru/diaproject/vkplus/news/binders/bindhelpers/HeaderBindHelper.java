package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.view.View;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.core.utils.DataConstants;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.news.viewholders.base.DataMainViewHolder;
import ru.diaproject.vkplus.profiles.VKProfileDetailsActivity;

public class HeaderBindHelper {
    private Context context;

    public HeaderBindHelper(Context context){
        this.context = context;
    }

    public void setHeader(final IDataOwner owner, Integer date, DataMainViewHolder holder){
        Glide.with(context).load(owner.getPhoto100()).into(holder.avatar);

        holder.name.setText(owner.getFullName());

        holder.date.setText(DateUtils.newsDateFormat(date, context));

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (owner instanceof IDataUser) {
                    Intent intent = new Intent(context, VKProfileDetailsActivity.class);
                    intent.putExtra(DataConstants.USER, owner);
                    context.startActivity(intent);
                }
            }
        });
    }
}
