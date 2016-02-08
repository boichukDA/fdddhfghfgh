package ru.diaproject.vkplus.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.model.baseitems.DataFriendItem;
import ru.diaproject.vkplus.model.groups.IDataGroup;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.news.viewholders.FriendSubItemViewHolder;


public class FriendSubItemAdapter extends RecyclerView.Adapter<FriendSubItemViewHolder> {
    private DataFriendItem entity;
    private Context context;
    private ColorScheme colorScheme;

    public FriendSubItemAdapter(Context context, DataFriendItem entity, ColorScheme colorScheme){
        this.entity = entity;
        this.context = context;
        this.colorScheme = colorScheme;
    }

    @Override
    public FriendSubItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_friend_subview_item, parent, false);
        FriendSubItemViewHolder holder = new FriendSubItemViewHolder(v);
        holder.applyColorScheme(colorScheme);
        return holder;
    }

    @Override
    public void onBindViewHolder(FriendSubItemViewHolder holder, int position) {
        IDataOwner owner = entity.getFriendOwners().get(position);

        Glide.with(context).load(owner.getPhoto100()).into(holder.avatar);

        if (owner instanceof IDataGroup){
            holder.surname.setVisibility(View.GONE);
            holder.name.setText(owner.getFullName());
        }else {
            holder.surname.setVisibility(View.VISIBLE);
            holder.name.setText(((IDataUser) owner).getFirstName());
            holder.surname.setText(((IDataUser)owner).getLastName());
        }

    }

    @Override
    public int getItemCount() {
        return entity.getFriendOwners().size();
    }
}
