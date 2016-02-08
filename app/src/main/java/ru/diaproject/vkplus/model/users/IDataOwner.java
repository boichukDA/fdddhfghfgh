package ru.diaproject.vkplus.model.users;


import android.text.Spannable;

public interface IDataOwner extends IDataObject{
    Spannable getFullName();
    OwnerSex getSex();
    Integer getPlaceholderResource();
    Integer getErrorResource();
    String getPhoto100();
}
