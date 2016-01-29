package ru.diaproject.vkplus.vkcore.queries.customs.items;

import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class VKPhotosItem extends VKItem<Photos> {

    public VKPhotosItem(VKUser user) {
        super(user);

        getQueryBuilder().setVKQueryType(VKQueryType.PHOTO);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(Photos.class);
    }

    public VKPreparedItem<Photos> get(){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.DEFAULT);
        return new PreparedItem<>(getQueryBuilder());
    }

}
