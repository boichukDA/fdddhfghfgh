package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.vkcore.queries.customs.items.VKNewsItem;
import ru.diaproject.vkplus.vkcore.queries.customs.items.VKPhotosItem;
import ru.diaproject.vkplus.vkcore.queries.customs.items.VKUsersItem;

public abstract class VKApi {

    public static VKNewsItem news(User user){
        return new VKNewsItem(user);
    }

    public static VKUsersItem users(User user){
        return new VKUsersItem(user);
    }

    public static VKPhotosItem photos(User user){
        return new VKPhotosItem(user);
    }
}
