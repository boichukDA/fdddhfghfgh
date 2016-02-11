package ru.diaproject.vkplus.profiles.model;

import android.content.Context;

import ru.diaproject.vkplus.model.users.extusers.DataUserExt;


public class UserDataContainer {
    public UserDataContainer(DataUserExt dataUserExt, Context context){
        shortInfo = new ShortInfoContainer(dataUserExt, context);
    }

    private ShortInfoContainer shortInfo;

}
