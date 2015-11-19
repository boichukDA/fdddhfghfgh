package ru.diaproject.vkplus.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.news.viewholders.FriendSubItemViewHolder;


public class FriendSubItemAdapter extends RecyclerView.Adapter<FriendSubItemViewHolder> {
    private List<User> users;
    private Context context;

    public FriendSubItemAdapter(Context context, List<User> users){
        this.users = users;
        this.context = context;
    }

    @Override
    public FriendSubItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_friend_subview_item, parent, false);
       return new FriendSubItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FriendSubItemViewHolder holder, int position) {
        Glide.with(context)
                .load(users.get(position).getPhoto100())
                .into(holder.avatar);

        holder.name.setText(users.get(position).getFirstName());
        holder.surname.setText(users.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
