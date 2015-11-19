package ru.diaproject.vkplus.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class SwipeRelativeLayout extends RelativeLayout {
    private static final int SWIPE_THRESHOLD = 200;
    private static final int SWIPE_VELOCITY_THRESHOLD = 50;

    private GestureDetector gestureDetector;
    private OnSwipeInterface eventsHandler;
    private MotionEvent previous = null;

    public SwipeRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public SwipeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        this.gestureDetector = new GestureDetector(context, new GestureListener());
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;

            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            if (eventsHandler != null)
                                eventsHandler.onSwipeRight();
                            result = true;
                        } else {
                            if (eventsHandler != null)
                                eventsHandler.onSwipeLeft();
                            result = true;
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            if (eventsHandler != null)
                                eventsHandler.onSwipeBottom();
                            result = true;
                        } else {
                            if (eventsHandler != null)
                                eventsHandler.onSwipeTop();
                            result = true;
                        }
                    }
                }
                return false;
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return false;
        }
    }
}
