package ru.diaproject.vkplus.news.model.baseitems;


import java.util.HashMap;

import ru.diaproject.vkplus.news.model.groups.IDataGroup;
import ru.diaproject.vkplus.news.model.users.IDataObject;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.model.users.IDataUser;

public interface IDataItem extends IDataObject{
    Integer getOwnerId();

    IDataOwner getItemOwner();

    void findOwner(HashMap<Integer, IDataUser> profiles,
              HashMap<Integer, IDataGroup> groups);
}
