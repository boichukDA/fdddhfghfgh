package ru.diaproject.vkplus.profiles.views;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ProfileImageView extends ImageView {
    public ProfileImageView(Context context) {
        super(context);
    }

    public ProfileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (width*3)/4;
        setMeasuredDimension(width, height);
    }
}
