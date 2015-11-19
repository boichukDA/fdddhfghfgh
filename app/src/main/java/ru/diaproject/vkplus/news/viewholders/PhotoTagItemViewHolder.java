package ru.diaproject.vkplus.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.view.SquareFrameLayout;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.views.PhotoViewContainer;

public class PhotoTagItemViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.name)
    public RobotoTextView name;

    @Bind(R.id.date)
    public RobotoTextView date;

    @Bind(R.id.avatar)
    public CircularImageView avatar;

    @Bind(R.id.marked_text)
    public RobotoTextView markedText;

    @Bind(R.id.photo_count)
    public RobotoTextView photoCount;

    @Bind(R.id.news_photo_photo_container)
    public PhotoViewContainer photoViewContainer;

    public PhotoTagItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void clear(NewsPagerCardFragment newsPagerCardFragment) {
        if (photoViewContainer!=null)
            photoViewContainer.clear(newsPagerCardFragment);
    }
}
