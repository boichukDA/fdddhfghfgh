package ru.diaproject.vkplus.news.binders;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.viewholders.PhotoItemViewHolder;

public class PhotoItemBinder extends DataPhotosBinder<PhotoItemViewHolder, IDataMainItem> {
    private NewsPagerCardFragment parent;

    public PhotoItemBinder(DataBindAdapter dataBindAdapter, NewsResponse items, NewsPagerCardFragment fragment) {
        super(fragment.getContext(), dataBindAdapter, items);
        this.parent = fragment;
    }

    @Override
    public PhotoItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_photoview_item, parent, false);
        return new PhotoItemViewHolder(v);
    }

    @Override
    public void bindViewHolder(PhotoItemViewHolder holder, int position) {
        IDataMainItem entity = getItem(position);
        setDataOwner(holder, entity);

        Photos photos = entity.getAttachmentPhotos();

        holder.photoCount.setTextColor(
                ColorUtils.setColorAlpha(
                        ContextCompat.getColor(parent.getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55));

        if (photos.getCount()> getMaxPhotosDisplay())
            holder.photoCount.setVisibility(View.VISIBLE);
        else holder.photoCount.setVisibility(View.GONE);

        holder.photoCount.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_photo_count_variants,
                photos.getCount() - getMaxPhotosDisplay(), photos.getCount() - getMaxPhotosDisplay()));

        if (!photos.getPhotos().isEmpty())
            setPhotos(photos, holder);
    }

}
