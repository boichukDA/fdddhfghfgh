package ru.diaproject.vkplus.news;


import java.io.Serializable;
import java.util.HashMap;

import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.groups.IDataGroup;
import ru.diaproject.vkplus.news.model.users.IDataUser;

public interface IDataFilter extends Serializable {
    boolean apply(IDataMainItem item, HashMap<Integer, IDataUser> profiles, HashMap<Integer, IDataGroup> groups);
}
