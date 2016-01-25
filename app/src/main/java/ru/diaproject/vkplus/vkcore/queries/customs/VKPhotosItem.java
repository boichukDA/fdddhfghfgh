package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class VKPhotosItem extends VKItem<Photos> {

    public VKPhotosItem(VKUser user) {
        super(user);
    }
}
