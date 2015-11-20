package ru.diaproject.vkplus.photoviewer.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.photoviewer.fragments.PhotoViewerItemFragment;

public class PhotoViewerAdapter extends FragmentPagerAdapter {
    private Photos photos;
    public PhotoViewerAdapter(FragmentManager fm, Photos photos) {
        super(fm);
        this.photos = photos;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoViewerItemFragment fragment = new PhotoViewerItemFragment();
        fragment.setPhotoInfo(photos.getPhotos().get(position));
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
}
