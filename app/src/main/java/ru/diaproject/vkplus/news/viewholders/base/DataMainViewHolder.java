package ru.diaproject.vkplus.news.viewholders.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.imageloading.ImageLoader;

public class DataMainViewHolder extends RecyclerView.ViewHolder {
    public RobotoTextView name;
    public RobotoTextView date;
    public CircularImageView avatar;

    public DataMainViewHolder(View itemView) {
        super(itemView);

        name = (RobotoTextView) itemView.findViewById(R.id.name);
        date = (RobotoTextView) itemView.findViewById(R.id.date);
        avatar = (CircularImageView) itemView.findViewById(R.id.avatar);
    }

    public void clear() {
        ImageLoader.clear(avatar);
    }
}
