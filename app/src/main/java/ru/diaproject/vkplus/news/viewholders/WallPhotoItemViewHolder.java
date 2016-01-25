package ru.diaproject.vkplus.news.viewholders;

import android.view.View;
import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.vkplus.R;

public class WallPhotoItemViewHolder extends DataPhotosViewHolder {
    public RobotoTextView photoCount;

    public WallPhotoItemViewHolder(View itemView) {
        super(itemView);
        photoCount = (RobotoTextView) itemView.findViewById(R.id.photo_count);
    }

    public void clear() {
        super.clear();
    }
}
