package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.vkcore.queries.customs.items.VKNewsItem;
import ru.diaproject.vkplus.vkcore.queries.customs.items.VKUsersItem;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public abstract class VKApi {

    public static VKNewsItem news(VKUser user){
        return new VKNewsItem(user);
    }

    public static VKUsersItem users(VKUser user){
        return new VKUsersItem(user);
    }
}
