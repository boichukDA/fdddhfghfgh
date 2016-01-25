package ru.diaproject.vkplus.news.binders;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.imageloading.ImageLoader;
import ru.diaproject.vkplus.news.adapters.FriendSubItemAdapter;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.baseitems.DataFriendItem;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.baseitems.IDataPostItem;
import ru.diaproject.vkplus.news.model.items.Friends;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.model.users.IDataUser;
import ru.diaproject.vkplus.news.model.users.OwnerSex;
import ru.diaproject.vkplus.news.viewholders.FriendItemViewHolder;

public class FriendItemBinder extends DataBinder<FriendItemViewHolder, IDataMainItem> {
    private Fragment parent;
    public FriendItemBinder(DataBindAdapter dataBindAdapter, NewsResponse items, Fragment fragment) {
        super(fragment.getContext(), dataBindAdapter, items);
        this.parent = fragment;
    }

    @Override
    public FriendItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_friendview_item, parent, false);
        FriendItemViewHolder holder = new FriendItemViewHolder(v);
        holder.friendsList.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false));
        return holder;
    }

    @Override
    public void bindViewHolder(FriendItemViewHolder holder, int position) {
        IDataMainItem entity = getItem(position);
        IDataOwner owner = entity.getItemOwner();

        setDataOwner(holder, entity);

        Friends friends = entity.getAttachmentFriends();

        if (owner.getSex().equals(OwnerSex.WOMAN))
           holder.addText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_friend_add_variant_woman, friends.getCount(), friends.getCount() ));
        else
            holder.addText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_friend_add_variant_man, friends.getCount(), friends.getCount() ));

        List<IDataUser> users = new ArrayList<>();
        /*for (Integer uid:friends.getFriends())
            if (items.getProfiles().containsKey(uid))
                users.add(items.getProfiles().get(uid));

        holder.friendsList.setAdapter(new FriendSubItemAdapter(parent.getContext(),users));*/
    }
}
