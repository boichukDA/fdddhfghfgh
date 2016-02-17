package ru.diaproject.vkplus.profiles.viewholders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.diaproject.vkplus.R;

public class KeyValueViewHolder extends RecyclerView.ViewHolder{
    public TextView keyText;
    public TextView valueText;

    public KeyValueViewHolder(View itemView) {
        super(itemView);

        keyText = (TextView) itemView.findViewById(R.id.profile_item_key);
        valueText = (TextView) itemView.findViewById(R.id.profile_item_value);
    }
}
