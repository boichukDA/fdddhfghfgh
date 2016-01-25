package ru.diaproject.vkplus.news.viewholders.items;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.vkplus.R;

public class DataAudiosViewHolder extends RecyclerView.ViewHolder{

    public LinearLayout mainLayout;
    public DataAudioItemHolder first;
    public DataAudioItemHolder second;
    public DataAudioItemHolder third;
    public DataAudioItemHolder fourth;
    public DataAudioItemHolder fifth;
    public RobotoTextView audioCount;

    public DataAudiosViewHolder(View itemView) {
        super(itemView);
        mainLayout = (LinearLayout) itemView.findViewById(R.id.news_audio_layout);
        first = new DataAudioItemHolder(itemView.findViewById(R.id.news_audio_first_item));
        second = new DataAudioItemHolder(itemView.findViewById(R.id.news_audio_second_item));
        third = new DataAudioItemHolder(itemView.findViewById(R.id.news_audio_third_item));
        fourth = new DataAudioItemHolder(itemView.findViewById(R.id.news_audio_fourth_item));
        fifth = new DataAudioItemHolder(itemView.findViewById(R.id.news_audio_fifth_item));
        audioCount = (RobotoTextView) itemView.findViewById(R.id.news_audio_count);
    }

    public void clear(){
        if (first!=null)
            first.clear();

        if (second!=null)
            second.clear();

        if (third!=null)
            third.clear();

        if (fourth!=null)
            fourth.clear();

        if (fifth!=null)
            fifth.clear();
    }
}
