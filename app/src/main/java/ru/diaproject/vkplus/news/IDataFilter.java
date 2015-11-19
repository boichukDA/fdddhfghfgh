package ru.diaproject.vkplus.news;


import java.io.Serializable;
import java.util.HashMap;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.users.User;

public interface IDataFilter extends Serializable {
    boolean apply(NewsEntityBase item, HashMap<Integer, User> profiles, HashMap<Integer, Group> groups);
}
