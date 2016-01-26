package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.news.model.attachments.doc.GifDocInfo;
import ru.diaproject.vkplus.news.viewholders.items.DataGifItemHolder;

public class GifItemBindHelper {

    private final Context context;

    public GifItemBindHelper(Context context){
        this.context = context;
    }

    public void setData(GifDocInfo info, DataGifItemHolder holder){
        holder.itemView.setVisibility(View.VISIBLE);

        String result = info.getTitle() + "." + info.getExt();
        holder.title.setText(result);

        Glide.with(context).load(info.getPreview().getPhoto().getSizeM().getSrc()).into(holder.imageView);
    }
    public void hideLayout(DataGifItemHolder holder){
        holder.itemView.setVisibility(View.GONE);
    }
}
