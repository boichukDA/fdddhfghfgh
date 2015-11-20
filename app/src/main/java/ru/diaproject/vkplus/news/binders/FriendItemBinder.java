package ru.diaproject.vkplus.news.binders;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.utils.ColorUtils;
import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.news.adapters.FriendSubItemAdapter;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.model.baseitems.NewsFriendItem;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.items.Friends;
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.news.viewholders.FriendItemViewHolder;

public class FriendItemBinder extends DataBinder<FriendItemViewHolder> {
    private Fragment parent;
    private Response items;
    public FriendItemBinder(DataBindAdapter dataBindAdapter, Response items, Fragment fragment) {
        super(dataBindAdapter);
        this.parent = fragment;
        this.items = items;
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
        NewsFriendItem entity = (NewsFriendItem) items.getItems().get(position);
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

        Friends friends = (Friends) entity.getAttachments();

        if (sex.equals(1))
           holder.addText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_friend_add_variant_woman, friends.getCount(), friends.getCount() ));
        else
            holder.addText.setText(parent.getContext().getResources().getQuantityString(R.plurals.news_friend_add_variant_man, friends.getCount(), friends.getCount() ));

        List<User> users = new ArrayList<>();
        for (Integer uid:friends.getFriends())
            if (items.getProfiles().containsKey(uid))
                users.add(items.getProfiles().get(uid));

        holder.friendsList.setAdapter(new FriendSubItemAdapter(parent.getContext(),users));
    }
}
