package ru.diaproject.vkplus.news.viewholders.items;


import android.view.View;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.news.viewholders.base.DataItemViewHolder;

public class DataVideosViewHolder extends DataItemViewHolder{
    public LinearLayout mainLayout;
    public DataVideoItemHolder main;
    public LinearLayout additionalLayout;
    public DataVideoItemHolder first;
    public DataVideoItemHolder second;
    public DataVideoItemHolder third;
    public RobotoTextView videoCount;

    public DataVideosViewHolder(View itemView) {
        super(itemView);
        mainLayout = (LinearLayout) itemView.findViewById(R.id.news_video_main_layout);
        main = new DataVideoItemHolder(itemView.findViewById(R.id.news_main_video_item));
        additionalLayout = (LinearLayout) itemView.findViewById(R.id.news_video_additional_layout);
        first = new DataVideoItemHolder( itemView.findViewById(R.id.news_video_item_first));
        second = new DataVideoItemHolder(itemView.findViewById(R.id.news_video_item_second));
        third = new DataVideoItemHolder(itemView.findViewById(R.id.news_video_item_third));
        videoCount = (RobotoTextView) itemView.findViewById(R.id.news_video_count);
    }

    public void clear() {
        if (main!= null)
            main.clear();

        if (main!= null)
            first.clear();

        if (main!= null)
            second.clear();

        if (main!= null)
            third.clear();
    }

    @Override
    public void applyColorScheme(ColorScheme scheme) {
        super.applyColorScheme(scheme);
        videoCount.setTextColor(scheme.getTextColor());

        main.applyColorScheme(scheme);
        first.applyColorScheme(scheme);
        second.applyColorScheme(scheme);
        third.applyColorScheme(scheme);
    }
}
