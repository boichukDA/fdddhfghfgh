package ru.diaproject.vkplus.news.viewholders.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.database.model.ColorScheme;

public class DataMainViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView date;
    public CircularImageView avatar;

    public DataMainViewHolder(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.name);
        date = (TextView) itemView.findViewById(R.id.date);
        avatar = (CircularImageView) itemView.findViewById(R.id.avatar);
    }

    public void clear() {
        Glide.clear(avatar);
    }

    public void applyColorScheme(ColorScheme scheme){
        date.setTextColor(
                ColorUtils.setColorAlpha(scheme.getTextColor(), ColorUtils.OPACITY_55));
        name.setTextColor(scheme.getTextColor());
    }
}
