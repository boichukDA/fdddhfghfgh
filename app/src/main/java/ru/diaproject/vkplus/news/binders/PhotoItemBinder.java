package ru.diaproject.vkplus.news.binders;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoItem;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.news.viewholders.PhotoItemViewHolder;

public class PhotoItemBinder extends DataBinder<PhotoItemViewHolder> {
    private Fragment parent;
    private Response items;
    public PhotoItemBinder(DataBindAdapter dataBindAdapter, Response items, Fragment fragment) {
        super(dataBindAdapter);
        this.parent = fragment;
        this.items = items;
    }

    @Override
    public PhotoItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_photoview_item, parent, false);
        return new PhotoItemViewHolder(v);
    }

    @Override
    public void bindViewHolder(PhotoItemViewHolder holder, int position) {
        NewsPhotoItem entity = (NewsPhotoItem) items.getItems().get(position);
        Integer sourceId = entity.getSourceId();
        Integer positiveSourceId = Math.abs(sourceId);
        String text = "";
        Byte sex = 2;
        String imageUrl = "";

        if (sourceId > 0 && items.getProfiles().containsKey(sourceId) ) {
            User user = items.getProfiles().get(sourceId);
            text = user.getFirstName() + " " + user.getLastName();
            imageUrl = user.getPhoto100();
        }
        if (sourceId < 0 && items.getGroups().containsKey(positiveSourceId) ) {
            Group group = items.getGroups().get(positiveSourceId);
            text = group.getName();
            imageUrl = group.getPhoto100();
        }
        BaseRequestOptions opt = new BaseRequestOptions() {
        };
        switch(sex){
            case 0:
                opt.placeholder(R.drawable.group_silhouette);
                opt.error(R.drawable.group_silhouette);
                break;
            case 1:
                opt.placeholder(R.drawable.woman_silhouette);
                opt.error(R.drawable.woman_silhouette);
                break;
            default:
                opt.placeholder(R.drawable.man_siluette);
                opt.error(R.drawable.man_siluette);
                break;
        }
        holder.name.setText(text);
        Glide.with(parent)
                .load(imageUrl)
                .apply(opt)
                .into(holder.avatar);

        holder.date.setTextColor(
                ColorUtils.setColorAlpha(
                        ContextCompat.getColor(parent.getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55));

        holder.date.setText(DateUtils.newsDateFormat(entity.getDate(), parent.getContext()));
        Photos photos = entity.getAttachments();

        holder.photoCount.setTextColor(
                ColorUtils.setColorAlpha(
                        ContextCompat.getColor(parent.getContext(), R.color.md_black_1000), ColorUtils.OPACITY_55));

        if (photos.getCount()>(7))
            holder.photoCount.setVisibility(View.VISIBLE);
        else holder.photoCount.setVisibility(View.GONE);

        holder.photoCount.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_photo_count_variants, photos.getCount() - 7, photos.getCount() - 7));

        BaseRequestOptions pictureOptions = new BaseRequestOptions() {
        };

        pictureOptions.format(DecodeFormat.PREFER_RGB_565);
        pictureOptions.placeholder(R.drawable.picture_placeholder);
        pictureOptions.error(R.drawable.picture_placeholder);

        holder.photoViewContainer.setData(photos, entity.getSourceId(), entity.getDate(), ((NewsPagerCardFragment)parent).getUser());
    }
}
