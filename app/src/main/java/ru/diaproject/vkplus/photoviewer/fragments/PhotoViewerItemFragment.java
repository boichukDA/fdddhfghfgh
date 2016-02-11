package ru.diaproject.vkplus.photoviewer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.imageloading.ImageLoader;
import ru.diaproject.vkplus.imageloading.OnCompletedListener;
import ru.diaproject.vkplus.model.attachments.photos.Photos;

public class PhotoViewerItemFragment extends Fragment {
    private ImageView view;
    private ProgressBar progress;

    private View.OnClickListener listener;
    private Photos photos;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.photo_viewer_item_layout, container, false);

        view = (ImageView) rootView.findViewById(R.id.photo_viewer_item_image);
        progress = (ProgressBar) rootView.findViewById(R.id.photo_viewer_item_progress);

        progress.setVisibility(View.VISIBLE);

        if(position < photos.getPhotos().size())
            ImageLoader.load(view, photos.getPhotos().get(position).getPhoto604(), new OnCompletedListener() {
                @Override
                public void onCompleted() {
                    progress.setVisibility(View.GONE);
                }
            });

        rootView.setOnClickListener(listener);
        return rootView;
    }

    public ImageView getImageView(){
        return view;
    }

    public void setClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
