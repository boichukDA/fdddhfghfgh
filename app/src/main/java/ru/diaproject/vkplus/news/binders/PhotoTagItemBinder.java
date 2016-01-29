package ru.diaproject.vkplus.news.binders;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.model.users.OwnerSex;
import ru.diaproject.vkplus.news.viewholders.PhotoTagItemViewHolder;

public class PhotoTagItemBinder extends DataPhotosBinder<PhotoTagItemViewHolder, IDataMainItem> {
    private NewsPagerCardFragment parent;

    public PhotoTagItemBinder(DataBindAdapter dataBindAdapter, NewsResponse items, NewsPagerCardFragment fragment) {
        super(fragment.getContext(), dataBindAdapter,items);
        this.parent = fragment;
    }

    @Override
    public PhotoTagItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_phototagview_item, parent, false);
        return new PhotoTagItemViewHolder(v);
    }

    @Override
    public void bindViewHolder(PhotoTagItemViewHolder holder, int position) {
        IDataMainItem entity = getItem(position);
        setDataOwner(holder, entity);
        IDataOwner owner = entity.getItemOwner();


        Photos photos = entity.getAttachmentPhotos();

        if (owner.getSex().equals(OwnerSex.MAN))
            holder.markedText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_marked_text_man, photos.getCount(), photos.getCount()));
        else
            holder.markedText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_marked_text_woman, photos.getCount(), photos.getCount()));

        holder.photoCount.setTextColor(
                ColorUtils.setColorAlpha(
                        ContextCompat.getColor(parent.getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55));

        if (photos.getCount()>getMaxPhotosDisplay())
            holder.photoCount.setVisibility(View.VISIBLE);
        else holder.photoCount.setVisibility(View.GONE);

        holder.photoCount.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_photo_count_variants, photos.getCount() - 7, photos.getCount()-7));
        setPhotos(entity, holder);
    }
}
