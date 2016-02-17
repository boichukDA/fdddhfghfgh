package ru.diaproject.vkplus.profiles.binders;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.profiles.VKProfileDetailsActivity;
import ru.diaproject.vkplus.profiles.model.UserDataContainer;
import ru.diaproject.vkplus.profiles.model.items.KeyValueItem;
import ru.diaproject.vkplus.profiles.viewholders.KeyValueViewHolder;

public class KeyValueBinder extends ItemDataBinder<KeyValueViewHolder> {

    public KeyValueBinder(VKProfileDetailsActivity context, UserDataContainer container, ColorScheme colorScheme) {
        super(context, container, colorScheme);
    }

    @Override
    public KeyValueViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_main_key_value_item, parent, false);
        KeyValueViewHolder holder = new KeyValueViewHolder(v);
        holder.itemView.setBackgroundColor(getColorScheme().getCardColor());
        holder.valueText.setTextColor(getColorScheme().getTextColor());
        holder.keyText.setTextColor(getColorScheme().getTextColor());
        return holder;
    }

    @Override
    public void bindViewHolder(KeyValueViewHolder holder, int position) {
        KeyValueItem item = (KeyValueItem) getContainer().get(position).getItem();

        holder.valueText.setText(item.getSpanValue());
        holder.keyText.setText(item.getSpanKey());
    }
}
