package ru.diaproject.vkplus.vkcore.queries.customs.photoitems;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;

public class VKPhotosItem extends VKItem<Photos> {

    public VKPhotosItem(User user) {
        super(user);

        getQueryBuilder().setVKQueryType(VKQueryType.PHOTO);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(Photos.class);
    }

    public VKPreparedItem<Photos> get(){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.GET);
        return new PreparedItem<>(getQueryBuilder());
    }

    public VKPhotoPreparedItem getById(){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.GET_BY_ID);
        return new VKPhotoPreparedItem(getQueryBuilder());
    }

}
