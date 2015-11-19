package ru.diaproject.vkplus.photoviewer.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;

public class PhotoViewerItemFragment extends Fragment {
    @Bind(R.id.photo_viewer_item_image)
    ImageView view;

    @Bind(R.id.photo_viewer_item_progress)
    ProgressBar progress;

    private PhotosInfo info;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId;
    private int viewX;
    private int viewY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.photo_viewer_item_layout, container, false);

        ButterKnife.bind(this, rootView);

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

        Glide.with(this).load(info.getPhoto604()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progress.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progress.setVisibility(View.GONE);
                return false;
            }
        }).into(view);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        return rootView;
    }

    public void setPhotoInfo(PhotosInfo info){
        this.info = info;
    }
    public ImageView getImageView(){
        return view;
    }

}
