package ru.diaproject.vkplus.photoviewer.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.photoviewer.fragments.PhotoViewerItemFragment;

public class PhotoViewerAdapter extends FragmentPagerAdapter {

    private Photos photos;
    private View.OnClickListener listener;

    public PhotoViewerAdapter(FragmentManager fm, Photos photos, View.OnClickListener onClickListener) {
        super(fm);
        this.photos = photos;
        listener = onClickListener;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoViewerItemFragment fragment = new PhotoViewerItemFragment();
        fragment.setPhotos(photos);
        fragment.setPosition(position);
        fragment.setClickListener(listener);
        return fragment;
    }

    @Override
    public int getCount() {
        return photos.getCount();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        PhotoViewerItemFragment fragment = (PhotoViewerItemFragment) object;
        Glide.clear(fragment.getImageView());
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }
}
