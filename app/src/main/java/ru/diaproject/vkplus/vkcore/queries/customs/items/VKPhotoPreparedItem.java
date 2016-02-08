package ru.diaproject.vkplus.vkcore.queries.customs.items;


import ru.diaproject.vkplus.model.items.Photos;
import ru.diaproject.vkplus.model.items.PhotosInfo;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;

public class VKPhotoPreparedItem extends PreparedItem<Photos> {

    public VKPhotoPreparedItem(VKQueryBuilder<Photos> preparedQuery) {
        super(preparedQuery);
    }

    @Override
    public VKPreparedItem<Photos> and(VKParameter key, Object value) {
        if (value instanceof Photos && key.equals(VKParameter.PHOTOS)){
            String val = photosToString((Photos) value);
            return super.and(key, val);
        }else
            return super.and(key, value);
    }

    @Override
    public VKPreparedItem<Photos> with(VKParameter key, Object value) {
        if (value instanceof Photos && key.equals(VKParameter.PHOTOS)){
            String val = photosToString((Photos) value);
            return super.with(key, val);
        }else
            return super.with(key, value);
    }

    private String photosToString(Photos photos){
        StringBuilder queryParamBuilder = new StringBuilder();
        for (PhotosInfo info:photos.getPhotos()){
            queryParamBuilder.append(info.getOwnerId());
            queryParamBuilder.append("_");
            queryParamBuilder.append(info.getId());
            queryParamBuilder.append("_");
            queryParamBuilder.append(info.getAccessToken());
            queryParamBuilder.append(",");
        }
        queryParamBuilder.deleteCharAt(queryParamBuilder.length() - 1);
        return queryParamBuilder.toString();
    }
}
