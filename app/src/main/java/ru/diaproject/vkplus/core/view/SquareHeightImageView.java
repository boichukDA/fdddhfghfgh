package ru.diaproject.vkplus.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareHeightImageView extends ImageView {
    public SquareHeightImageView(Context context) {
        super(context);
    }

    public SquareHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareHeightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
