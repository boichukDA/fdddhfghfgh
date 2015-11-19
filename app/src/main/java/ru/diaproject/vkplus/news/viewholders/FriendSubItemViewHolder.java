package ru.diaproject.vkplus.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;

public class FriendSubItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.friend_item_avatar)
    public CircularImageView avatar;

    @Bind(R.id.friend_item_name)
    public RobotoTextView name;

    @Bind(R.id.friend_item_surname)
    public RobotoTextView surname;

    public FriendSubItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
