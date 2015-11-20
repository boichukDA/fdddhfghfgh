package ru.diaproject.vkplus.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;

public class FriendItemViewHolder extends RecyclerView.ViewHolder{
    @Bind(R.id.name)
    public RobotoTextView name;

    @Bind(R.id.date)
    public RobotoTextView date;

    @Bind(R.id.avatar)
    public CircularImageView avatar;

    @Bind(R.id.friend_add_text)
    public RobotoTextView addText;

    @Bind(R.id.friend_list)
    public RecyclerView friendsList;

    public FriendItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void clear() {
        Glide.clear(avatar);
    }
}
