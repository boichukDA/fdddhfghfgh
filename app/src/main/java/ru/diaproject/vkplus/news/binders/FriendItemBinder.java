package ru.diaproject.vkplus.news.binders;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.news.adapters.FriendSubItemAdapter;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.model.NewsResponse;
import ru.diaproject.vkplus.model.newsitems.DataFriendItem;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;
import ru.diaproject.vkplus.model.attachments.Friends;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.model.users.OwnerSex;
import ru.diaproject.vkplus.news.viewholders.FriendItemViewHolder;

public class FriendItemBinder extends DataBinder<FriendItemViewHolder, IDataMainItem> {
    private Fragment parent;
    private ColorScheme colorScheme;
    public FriendItemBinder(DataBindAdapter dataBindAdapter, NewsResponse items, Fragment fragment) {
        super(fragment.getContext(), dataBindAdapter, items);
        this.parent = fragment;
        colorScheme = ((NewsPagerCardFragment)parent).getUser().getColorScheme();
    }

    @Override
    public FriendItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_friendview_item, parent, false);
        ((CardView)v).setCardBackgroundColor(colorScheme.getCardColor());

        FriendItemViewHolder holder = new FriendItemViewHolder(v);
        holder.friendsList.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false));

        holder.addText.setTextColor(colorScheme.getTextColor());
        holder.applyColorScheme(colorScheme);
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

        holder.friendsList.setAdapter(new FriendSubItemAdapter(parent.getContext(), (DataFriendItem) entity, colorScheme));
    }
}
