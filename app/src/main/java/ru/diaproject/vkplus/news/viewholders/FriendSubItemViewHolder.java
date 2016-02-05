package ru.diaproject.vkplus.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;

public class FriendSubItemViewHolder extends RecyclerView.ViewHolder {
    public CircularImageView avatar;
    public RobotoTextView name;
    public RobotoTextView surname;

    public FriendSubItemViewHolder(View itemView) {
        super(itemView);
        avatar = (CircularImageView) itemView.findViewById(R.id.friend_item_avatar);
        name = (RobotoTextView) itemView.findViewById(R.id.friend_item_name);
        surname = (RobotoTextView) itemView.findViewById(R.id.friend_item_surname);
    }

    public void applyColorScheme(ColorScheme colorScheme) {
        name.setTextColor(colorScheme.getTextColor());
        surname.setTextColor(colorScheme.getTextColor());
    }
}
