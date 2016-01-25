package ru.diaproject.vkplus.news.viewholders;

import android.view.View;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.vkplus.R;

public class PhotoTagItemViewHolder extends DataPhotosViewHolder {
    public RobotoTextView markedText;
    public RobotoTextView photoCount;

    public PhotoTagItemViewHolder(View itemView) {
        super(itemView);
        markedText = (RobotoTextView) itemView.findViewById(R.id.marked_text);
        photoCount = (RobotoTextView) itemView.findViewById(R.id.photo_count);
    }

    public void clear() {
        super.clear();
    }
}
