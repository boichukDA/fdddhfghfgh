package ru.diaproject.vkplus.profiles.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;

import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.profiles.VKProfileDetailsActivity;
import ru.diaproject.vkplus.profiles.binders.ItemDataBinder;
import ru.diaproject.vkplus.profiles.binders.KeyValueBinder;
import ru.diaproject.vkplus.profiles.binders.RelationBinder;
import ru.diaproject.vkplus.profiles.model.ProfileItemType;
import ru.diaproject.vkplus.profiles.model.UserDataContainer;

public class UserDataAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {
    private UserDataContainer container;
    private VKProfileDetailsActivity context;
    private HashMap<ProfileItemType, ItemDataBinder> binders;
    private ColorScheme colorScheme;

    public UserDataAdapter(VKProfileDetailsActivity context, UserDataContainer container, ColorScheme colorScheme){
        this.context = context;
        this.container = container;
        this.colorScheme = colorScheme;

        binders = new HashMap<>();
        binders.put(ProfileItemType.KEY_VALUE, new KeyValueBinder(this.context, container, colorScheme));
        binders.put(ProfileItemType.RELATION, new RelationBinder(this.context, container, colorScheme));
    }

    @Override
    public int getItemCount() {
        return container.getItemCount();
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        int itemId = holder.getItemViewType();
        ItemDataBinder<T> binder =  getDataBinder(itemId);
        binder.bindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return container.get(position).getType().ordinal();
    }

    public ItemDataBinder<T> getDataBinder(int viewType) {
        return binders.get(ProfileItemType.values()[viewType]);
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }
}
