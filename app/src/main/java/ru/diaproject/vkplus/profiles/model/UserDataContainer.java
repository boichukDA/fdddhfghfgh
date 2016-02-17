package ru.diaproject.vkplus.profiles.model;

import android.content.Context;

import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.profiles.model.items.InfoItem;


public class UserDataContainer {
    public UserDataContainer(DataUserExt dataUserExt, Context context){
        shortInfo = new ShortInfoContainer(dataUserExt, context);
    }

    private ShortInfoContainer shortInfo;

    public int getItemCount() {
        return shortInfo.getItemCount();
    }
    public InfoItem get(int position){
        if (position< shortInfo.getItemCount())
            return shortInfo.get(position);
        return null;
    }
}
