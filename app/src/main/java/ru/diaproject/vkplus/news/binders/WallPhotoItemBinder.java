package ru.diaproject.vkplus.news.binders;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.model.NewsResponse;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.news.viewholders.WallPhotoItemViewHolder;

public class WallPhotoItemBinder extends DataPhotosBinder<WallPhotoItemViewHolder, IDataMainItem> {

    private NewsPagerCardFragment parent;
    private ColorScheme colorScheme;

    public WallPhotoItemBinder(DataBindAdapter dataBindAdapter, NewsResponse items, NewsPagerCardFragment fragment) {
        super(fragment.getContext(), dataBindAdapter, items);
        this.parent = fragment;
        colorScheme = parent.getUser().getColorScheme();
    }

    @Override
    public WallPhotoItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_wallphotoview_item, parent, false);
        ((CardView)v).setCardBackgroundColor(colorScheme.getCardColor());

        WallPhotoItemViewHolder holder = new WallPhotoItemViewHolder(v);
        holder.applyColorScheme(colorScheme);

        holder.photoCount.setTextColor(
                ColorUtils.setColorAlpha(colorScheme.getTextColor(), ColorUtils.OPACITY_55));

        return holder;
    }

    @Override
    public void bindViewHolder(WallPhotoItemViewHolder holder, int position) {
        IDataMainItem entity = getItem(position);
        setDataOwner(holder, entity);

        Photos photos = entity.getAttachmentPhotos();

        if (photos.getCount() > getMaxPhotosDisplay())
            holder.photoCount.setVisibility(View.VISIBLE);
        else holder.photoCount.setVisibility(View.GONE);

        holder.photoCount.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_photo_count_variants,
                photos.getCount() - getMaxPhotosDisplay(), photos.getCount() - getMaxPhotosDisplay()));
        setPhotos(entity, holder);
    }

}
