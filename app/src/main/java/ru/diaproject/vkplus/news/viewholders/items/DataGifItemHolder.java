package ru.diaproject.vkplus.news.viewholders.items;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.news.viewholders.base.DataItemViewHolder;

public class DataGifItemHolder extends DataItemViewHolder{

    public final TextView title;
    public final ImageView imageView;

    public DataGifItemHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.news_gif_title);
        imageView = (ImageView) itemView.findViewById(R.id.news_gif_image);
    }

    @Override
    public void applyColorScheme(ColorScheme scheme) {
        super.applyColorScheme(scheme);
    }
}
