package ru.diaproject.vkplus.photoviewer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.imageloading.ImageLoader;
import ru.diaproject.vkplus.imageloading.OnCompletedListener;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;

public class PhotoViewerItemFragment extends Fragment {
    private ImageView view;
    private ProgressBar progress;

    private PhotosInfo info;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId;
    private int viewX;
    private int viewY;

    private View.OnClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.photo_viewer_item_layout, container, false);

        view = (ImageView) rootView.findViewById(R.id.photo_viewer_item_image);
        progress = (ProgressBar) rootView.findViewById(R.id.photo_viewer_item_progress);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case MotionEvent.ACTION_DOWN: {
                        final int pointerIndex = MotionEventCompat.getActionIndex(event);
                        final float x = MotionEventCompat.getX(event, pointerIndex);
                        final float y = MotionEventCompat.getY(event, pointerIndex);

                        mLastTouchX = x;
                        mLastTouchY = y;
                        mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        final int pointerIndex =
                                MotionEventCompat.findPointerIndex(event, mActivePointerId);

                        final float x = MotionEventCompat.getX(event, pointerIndex);
                        final float y = MotionEventCompat.getY(event, pointerIndex);

                        final float dx = x - mLastTouchX;
                        final float dy = y - mLastTouchY;

                        view.setY(view.getX() + dy);

                        view.invalidate();

                        // Remember this touch position for the next move event
                        mLastTouchX = x;
                        mLastTouchY = y;

                        break;
                    }
                }
                return false;
            }
        });

        progress.setVisibility(View.VISIBLE);

        ImageLoader.load(view, info.getPhoto604(), new OnCompletedListener() {
            @Override
            public void onCompleted() {
                progress.setVisibility(View.GONE);
            }
        });

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        rootView.setOnClickListener(listener);
        return rootView;
    }

    public void setPhotoInfo(PhotosInfo info){
        this.info = info;
    }
    public ImageView getImageView(){
        return view;
    }

    public void setClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
