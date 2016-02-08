package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.view.View;

import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.model.attachments.doc.GifDocInfo;
import ru.diaproject.vkplus.news.viewholders.items.DataGifsViewHolder;

public class GifBindHelper {

    private final Context context;
    private final int maxGifDisplay;
    private final GifItemBindHelper helper;

    public GifBindHelper(Context context, int maxGifDisplay){
        this.context = context;
        this.maxGifDisplay = maxGifDisplay;

        helper = new GifItemBindHelper(context);
    }
    public void setData(List<DocInfo> infoes, DataGifsViewHolder holder){
        holder.itemView.setVisibility(View.VISIBLE);
        int size = infoes.size();
        if (size == 1){
            helper.setData((GifDocInfo) infoes.get(0), holder.first);
            helper.hideLayout(holder.second);
            helper.hideLayout(holder.third);
        }else if (size == 2){
            helper.setData((GifDocInfo) infoes.get(0), holder.first);
            helper.setData((GifDocInfo) infoes.get(1), holder.second);
            helper.hideLayout(holder.third);
        }else if (size >= 3){
            helper.setData((GifDocInfo) infoes.get(0), holder.first);
            helper.setData((GifDocInfo) infoes.get(1), holder.second);
            helper.setData((GifDocInfo) infoes.get(2), holder.third);
        }
        if (infoes.size()> maxGifDisplay) {
            holder.gifCount.setVisibility(View.VISIBLE);

            holder.gifCount.setText(context.getResources().getQuantityString(R.plurals.news_video_count_variants,
                    size - maxGifDisplay, size - maxGifDisplay));
        }
        else holder.gifCount.setVisibility(View.GONE);
    }
    public void hideLayout(DataGifsViewHolder holder){
        holder.itemView.setVisibility(View.GONE);
    }
}
