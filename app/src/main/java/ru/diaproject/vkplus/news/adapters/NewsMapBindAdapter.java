package ru.diaproject.vkplus.news.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.databinders.EnumMapBindAdapter;
import ru.diaproject.vkplus.news.binders.FriendItemBinder;
import ru.diaproject.vkplus.news.binders.PhotoItemBinder;
import ru.diaproject.vkplus.news.binders.PhotoTagItemBinder;
import ru.diaproject.vkplus.news.binders.PostItemBinder;
import ru.diaproject.vkplus.news.binders.WallPhotoItemBinder;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.viewholders.base.DataMainViewHolder;

public class NewsMapBindAdapter extends EnumMapBindAdapter<FilterType>{
    private NewsResponse items;
    private NewsPagerCardFragment parent;
    private final SparseBooleanArray mCollapsedStatus;

    public NewsMapBindAdapter(NewsResponse items, NewsPagerCardFragment context){
        setData(items);
        this.parent = context;
        mCollapsedStatus = new SparseBooleanArray();

        putBinder(FilterType.POST, new PostItemBinder(this, items, parent, mCollapsedStatus ));
        putBinder(FilterType.PHOTO, new PhotoItemBinder(this, items, parent));
        putBinder(FilterType.WALL_PHOTO, new WallPhotoItemBinder(this, items, parent));
        putBinder(FilterType.PHOTO_TAG, new PhotoTagItemBinder(this, items, parent));
        putBinder(FilterType.FRIEND, new FriendItemBinder(this, items, parent));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int itemId = viewHolder.getItemViewType();
        DataBinder binder =  getDataBinder(itemId);
       binder.bindViewHolder((DataMainViewHolder) viewHolder, position);
    }
    public final void setData(NewsResponse response){
        this.items = response;
    }

    @Override
    public int getItemCount() {
        return items.getListItems().size();
    }

    @Override
    public final int getItemViewType(int position) {
        return items.getListItems().get(position).getType().ordinal();
    }

    @Override
    public final <T extends DataBinder> T getDataBinder(int viewType) {
        return getDataBinder(FilterType.values()[viewType]);
    }
}
