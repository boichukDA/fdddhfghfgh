package ru.diaproject.vkplus.core.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;

import ru.diaproject.ui.circularimageview.RobotoExpandableTextView;

public class RobotoImageExpandableTextView extends RobotoExpandableTextView {
    public RobotoImageExpandableTextView(Context context) {
        super(context);
    }

    public RobotoImageExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setText(@Nullable final CharSequence text) {
        RobotoImageExpandableTextView.super.setText(text);
    }

    @Override
    public void setText(@Nullable final CharSequence text, @NonNull final SparseBooleanArray collapsedStatus, final int position) {
        RobotoImageExpandableTextView.super.setText(text, collapsedStatus, position);
    }
}
