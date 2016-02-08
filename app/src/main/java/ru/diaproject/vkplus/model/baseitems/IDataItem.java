package ru.diaproject.vkplus.model.baseitems;


import java.util.HashMap;

import ru.diaproject.vkplus.model.groups.IDataGroup;
import ru.diaproject.vkplus.model.users.IDataObject;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.model.users.IDataUser;

public interface IDataItem extends IDataObject{
    Integer getOwnerId();

    IDataOwner getItemOwner();

    void findOwner(HashMap<Integer, IDataUser> profiles,
              HashMap<Integer, IDataGroup> groups);
}
