package ru.diaproject.vkplus.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.views.PhotoViewContainer;

public class PhotoItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.name)
    public RobotoTextView name;

    @Bind(R.id.date)
    public RobotoTextView date;

    @Bind(R.id.avatar)
    public CircularImageView avatar;

    @Bind(R.id.photo_count)
    public RobotoTextView photoCount;

    @Bind(R.id.news_photo_photo_container)
    public PhotoViewContainer photoViewContainer;

    public PhotoItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void clear() {
        if (avatar!=null)
            Glide.clear(avatar);

        if (photoViewContainer!=null)
            photoViewContainer.clear();

    }
}
