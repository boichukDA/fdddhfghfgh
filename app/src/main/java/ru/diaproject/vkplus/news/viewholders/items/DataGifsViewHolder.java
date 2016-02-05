package ru.diaproject.vkplus.news.viewholders.items;


import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.news.viewholders.base.DataItemViewHolder;

public class DataGifsViewHolder extends DataItemViewHolder{
    public final RobotoTextView gifCount;
    public DataGifItemHolder first;
    public DataGifItemHolder second;
    public DataGifItemHolder third;

    public DataGifsViewHolder(View itemView) {
        super(itemView);

        first = new DataGifItemHolder(itemView.findViewById(R.id.news_giff_item_first));
        second = new DataGifItemHolder(itemView.findViewById(R.id.news_giff_item_second));
        third = new DataGifItemHolder(itemView.findViewById(R.id.news_giff_item_third));

        gifCount = (RobotoTextView) itemView.findViewById(R.id.news_gif_count);
    }

    @Override
    public void applyColorScheme(ColorScheme scheme) {
        super.applyColorScheme(scheme);

        gifCount.setTextColor(scheme.getTextColor());

        first.applyColorScheme(scheme);
        second.applyColorScheme(scheme);
        third.applyColorScheme(scheme);
    }
}
