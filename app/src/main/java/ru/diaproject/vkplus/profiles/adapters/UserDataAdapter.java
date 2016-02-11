package ru.diaproject.vkplus.profiles.adapters;

import android.support.v7.widget.RecyclerView;

import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.databinders.EnumMapBindAdapter;
import ru.diaproject.vkplus.profiles.model.ProfileItemType;

public class UserDataAdapter extends EnumMapBindAdapter<ProfileItemType> {
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public <T extends DataBinder> T getDataBinder(int viewType) {
        return null;
    }
}
