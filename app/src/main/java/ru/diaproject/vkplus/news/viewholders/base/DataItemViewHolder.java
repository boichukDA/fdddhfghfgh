package ru.diaproject.vkplus.news.viewholders.base;


import android.view.View;

import ru.diaproject.vkplus.database.model.ColorScheme;

public class DataItemViewHolder {
    public View itemView;

    public DataItemViewHolder(View itemView) {
        this.itemView = itemView;
    }

    public void applyColorScheme(ColorScheme scheme){

    }
}
