package ru.diaproject.vkplus.news.binders;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoTagItem;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.news.viewholders.PhotoTagItemViewHolder;

public class PhotoTagItemBinder extends DataBinder<PhotoTagItemViewHolder> {
    private Fragment parent;
    private Response items;
    public PhotoTagItemBinder(DataBindAdapter dataBindAdapter, Response items, Fragment fragment) {
        super(dataBindAdapter);
        this.parent = fragment;
        this.items = items;
    }

    @Override
    public PhotoTagItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_phototagview_item, parent, false);
        return new PhotoTagItemViewHolder(v);
    }

    @Override
    public void bindViewHolder(PhotoTagItemViewHolder holder, int position) {
        NewsPhotoTagItem entity = (NewsPhotoTagItem) items.getItems().get(position);
        Integer sourceId = entity.getSourceId();
        Integer positiveSourceId = Math.abs(sourceId);
        String text = "";
        String imageUrl = "";
        Byte sex = 2;
        if (sourceId > 0 && items.getProfiles().containsKey(sourceId) ) {
            User user = items.getProfiles().get(sourceId);
            sex = user.getSex();
            text = user.getFirstName() + " " + user.getLastName();
            imageUrl = user.getPhoto100();
        }
        if (sourceId < 0 && items.getGroups().containsKey(positiveSourceId) ) {
            Group group = items.getGroups().get(positiveSourceId);
            text = group.getName();
            sex = 0;
            imageUrl = group.getPhoto100();
        }

        switch(sex){
            case 0:
                Glide.with(parent)
                        .load(imageUrl)
                        .placeholder(R.drawable.group_silhouette)
                        .error(R.drawable.group_silhouette)
                        .into(holder.avatar);
                break;
            case 1:
                Glide.with(parent)
                        .load(imageUrl)
                        .placeholder(R.drawable.woman_silhouette)
                        .error(R.drawable.woman_silhouette)
                        .into(holder.avatar);
                break;
            default:
                Glide.with(parent)
                        .load(imageUrl)
                        .placeholder(R.drawable.man_siluette)
                        .error(R.drawable.man_siluette)
                        .into(holder.avatar);
                break;
        }

        holder.name.setText(text);

        holder.date.setTextColor(
                ColorUtils.setColorAlpha(
                        ContextCompat.getColor(parent.getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55));

        holder.date.setText(DateUtils.newsDateFormat(entity.getDate(), parent.getContext()));

        Photos photos = entity.getAttachments();

        if (sex.equals(Byte.valueOf("2")))
            holder.markedText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_marked_text_man, photos.getCount(), photos.getCount()));
        else
            holder.markedText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_marked_text_woman, photos.getCount(), photos.getCount()));

        holder.photoCount.setTextColor(
                ColorUtils.setColorAlpha(
                        ContextCompat.getColor(parent.getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55));

        if (photos.getCount()>(7))
            holder.photoCount.setVisibility(View.VISIBLE);
        else holder.photoCount.setVisibility(View.GONE);

        holder.photoCount.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_photo_count_variants, photos.getCount()-7, photos.getCount()-7));
    }

}
