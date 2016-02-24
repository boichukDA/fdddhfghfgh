package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.vkcore.queries.customs.newsitems.VKNewsItem;
import ru.diaproject.vkplus.vkcore.queries.customs.photoitems.VKPhotosItem;
import ru.diaproject.vkplus.vkcore.queries.customs.useritems.UserManager;

public abstract class VKApi {

    public static VKNewsItem news(User user){
        return new VKNewsItem(user);
    }

    public static UserManager users(User user){
        return new UserManager(user);
    }


    public static VKPhotosItem photos(User user){
        return new VKPhotosItem(user);
    }
}
