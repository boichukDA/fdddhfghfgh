package ru.diaproject.vkplus.profiles.binders;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.profiles.VKProfileDetailsActivity;
import ru.diaproject.vkplus.profiles.model.UserDataContainer;

public abstract class ItemDataBinder<T extends RecyclerView.ViewHolder> {
    private UserDataContainer container;
    private VKProfileDetailsActivity context;
    private ColorScheme colorScheme;

    public ItemDataBinder(VKProfileDetailsActivity context, UserDataContainer container, ColorScheme colorScheme){
        this.context = context;
        this.container = container;
        this.colorScheme = colorScheme;
    }
    abstract public T newViewHolder(ViewGroup parent);

    public abstract void bindViewHolder(T holder, int position);

    protected UserDataContainer getContainer(){
        return container;
    }

    public VKProfileDetailsActivity getContext() {
        return context;
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }
}
