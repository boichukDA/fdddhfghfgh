package ru.diaproject.ui.circularimageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devspark.robototextview.widget.RobotoTextView;

public class RobotoExpandableTextView extends LinearLayout implements View.OnClickListener {
    private static final int DEFAULT_MAX_LINES = 10;

    private boolean mRelayout;

    private boolean mCollapsed = true; // Show short version as default.

    private int mCollapsedHeight;

    private int mTextHeightWithMaxLines;

    private int mMaxCollapsedLines;

    private int mMarginBetweenTxtAndBottom;

    private int mAnimationDuration;

    private float mAnimAlphaStart;

    private boolean mAnimating;

    private String mExpandText;
    private String mCollapseText;

    protected RobotoTextView expandTextView;
    protected TextView collapseTextView;

    private OnExpandStateChangeListener mListener;

    private SparseBooleanArray mCollapsedStatus;
    private int mPosition;

    private int mCollapseColor;
    private int mExpandColor;

    public RobotoExpandableTextView(Context context) {
        super(context);
    }

    public RobotoExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }
    private void init(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.expandable_text_view_layout, null);
        expandTextView = (RobotoTextView) view.findViewById(R.id.expandable_text);
        expandTextView.setOnClickListener(this);

        collapseTextView = (TextView) view.findViewById(R.id.collapse_text);
        expandTextView.setOnClickListener(this);

        addView(view);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_maxLines, DEFAULT_MAX_LINES);
        mAnimationDuration = 300;
        mAnimAlphaStart = 0.7f;
        mExpandText = typedArray.getString(R.styleable.ExpandableTextView_expandText);
        mCollapseText = typedArray.getString(R.styleable.ExpandableTextView_collapseText);

        mCollapseColor = typedArray.getColor(R.styleable.ExpandableTextView_collapseColor, Color.parseColor("#FFF000"));
        mExpandColor = typedArray.getColor(R.styleable.ExpandableTextView_expandColor, Color.parseColor("#000000"));

        typedArray.recycle();

        collapseTextView.setTextColor(mCollapseColor);
        expandTextView.setTextColor(mExpandColor);

        setOrientation(LinearLayout.VERTICAL);
        setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        try {
            if (collapseTextView.getVisibility() != View.VISIBLE) {
                return;
            }

            mCollapsed = !mCollapsed;
            collapseTextView.setText(mCollapsed ? mExpandText : mCollapseText);

            if (mCollapsedStatus != null) {
                mCollapsedStatus.put(mPosition, mCollapsed);
            }

            // mark that the animation is in progress
            mAnimating = true;

            Animation animation;
            if (mCollapsed) {
                animation = new ExpandCollapseAnimation(this, getHeight(), mCollapsedHeight);
            } else {
                animation = new ExpandCollapseAnimation(this, getHeight(), getHeight() +
                        mTextHeightWithMaxLines - expandTextView.getHeight());
            }

            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    applyAlphaAnimation(expandTextView, mAnimAlphaStart);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // clear animation here to avoid repeated applyTransformation() calls
                    clearAnimation();
                    // clear the animation flag
                    mAnimating = false;

                    // notify the listener
                    if (mListener != null) {
                        mListener.onExpandStateChanged(expandTextView, !mCollapsed);
                    }

                    if (mCollapsed) {
                        expandTextView.setEllipsize(TextUtils.TruncateAt.END);
                        expandTextView.setMaxLines(mMaxCollapsedLines);
                        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        requestLayout();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            clearAnimation();
            startAnimation(animation);
        }
        catch(Throwable e){
            Log.e("onClick", "exception", e);
        }
    }

    public void setText(Spannable s , TextView.BufferType type){
        mRelayout = true;
        expandTextView.setText(s,type);
        setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
    }
    public void setText(@Nullable CharSequence text) {
        mRelayout = true;
        expandTextView.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    public void setText(@Nullable Spannable text,  TextView.BufferType type, @NonNull SparseBooleanArray collapsedStatus, int position) {
        mCollapsedStatus = collapsedStatus;
        mPosition = position;
        boolean isCollapsed = collapsedStatus.get(position, true);
        clearAnimation();
        mCollapsed = isCollapsed;
        collapseTextView.setText(mCollapsed ? mExpandText : mCollapseText);
        setText(text, type);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    public void setText(@Nullable CharSequence text, @NonNull SparseBooleanArray collapsedStatus, int position) {
        mCollapsedStatus = collapsedStatus;
        mPosition = position;
        boolean isCollapsed = collapsedStatus.get(position, true);
        clearAnimation();
        mCollapsed = isCollapsed;
        collapseTextView.setText(mCollapsed ? mExpandText : mCollapseText);
        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mAnimating;
    }

    private static int getRealTextViewHeight(@NonNull TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // If no change, measure and return
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;

        // Setup with optimistic case
        // i.e. Everything fits. No button needed
        collapseTextView.setVisibility(View.GONE);
        expandTextView.setMaxLines(Integer.MAX_VALUE);

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // If the text fits in collapsed mode, we are done.
        if (expandTextView.getLineCount() <= mMaxCollapsedLines) {
            return;
        }

        // Saves the text height w/ max lines
        mTextHeightWithMaxLines = getRealTextViewHeight(expandTextView);

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (mCollapsed) {
            expandTextView.setMaxLines(mMaxCollapsedLines);
        }
        collapseTextView.setVisibility(View.VISIBLE);

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mCollapsed) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            expandTextView.post(new Runnable() {
                @Override
                public void run() {
                    mMarginBetweenTxtAndBottom = getHeight() - expandTextView.getHeight();
                }
            });
            // Saves the collapsed height of this ViewGroup
            mCollapsedHeight = getMeasuredHeight();
        }
    }
    class ExpandCollapseAnimation extends Animation {
        private final View mTargetView;
        private final int mStartHeight;
        private final int mEndHeight;

        public ExpandCollapseAnimation(View view, int startHeight, int endHeight) {
            mTargetView = view;
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            setDuration(mAnimationDuration);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int)((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight);
            expandTextView.setMaxHeight(newHeight - mMarginBetweenTxtAndBottom);
            if (Float.compare(mAnimAlphaStart, 1.0f) != 0) {
                applyAlphaAnimation(expandTextView, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart));
            }
            mTargetView.getLayoutParams().height = newHeight;
            mTargetView.requestLayout();
        }

        @Override
        public void initialize( int width, int height, int parentWidth, int parentHeight ) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds( ) {
            return true;
        }
    }

    private static boolean isPostHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void applyAlphaAnimation(View view, float alpha) {
        if (isPostHoneycomb()) {
            view.setAlpha(alpha);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
            // make it instant
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            view.startAnimation(alphaAnimation);
        }
    }

    public interface OnExpandStateChangeListener {
        void onExpandStateChanged(TextView textView, boolean isExpanded);
    }
}

