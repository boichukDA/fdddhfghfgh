package ru.diaproject.vkplus.news.viewholders.items;

import android.view.View;

import ru.diaproject.ui.circularimageview.RobotoExpandableTextView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.viewholders.DataPhotosViewHolder;

public class CopyHistoryViewHolder extends DataPhotosViewHolder {

    public RobotoExpandableTextView firstCopyTextView;
    public DataVideosViewHolder mainVideosholder;
    public DataAudiosViewHolder mainAudioViewHolder;

    public CopyHistoryViewHolder(View itemView){
        super(itemView);
        firstCopyTextView = (RobotoExpandableTextView) itemView.findViewById(R.id.news_copy_history_text);
        mainVideosholder = new DataVideosViewHolder(itemView);
        mainAudioViewHolder = new DataAudiosViewHolder(itemView);
    }

    @Override
    public void clear() {
        super.clear();

        if (mainVideosholder != null)
            mainVideosholder.clear();
    }
}
