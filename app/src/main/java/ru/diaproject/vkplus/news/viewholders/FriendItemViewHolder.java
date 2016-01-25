package ru.diaproject.vkplus.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.viewholders.base.DataMainViewHolder;

public class FriendItemViewHolder extends DataMainViewHolder {

    public RobotoTextView addText;
    public RecyclerView friendsList;

    public FriendItemViewHolder(View itemView) {
        super(itemView);
        addText = (RobotoTextView) itemView.findViewById(R.id.friend_add_text);
        friendsList = (RecyclerView) itemView.findViewById(R.id.friend_list);
    }
}
