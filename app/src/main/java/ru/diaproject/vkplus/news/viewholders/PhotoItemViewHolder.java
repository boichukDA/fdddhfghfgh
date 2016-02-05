package ru.diaproject.vkplus.news.viewholders;

import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;

public class PhotoItemViewHolder extends DataPhotosViewHolder {
    public RobotoTextView photoCount;

    public PhotoItemViewHolder(View itemView) {
        super(itemView);
        photoCount = (RobotoTextView) itemView.findViewById(R.id.photo_count);
    }

    public void clear() {
        super.clear();
    }


}
