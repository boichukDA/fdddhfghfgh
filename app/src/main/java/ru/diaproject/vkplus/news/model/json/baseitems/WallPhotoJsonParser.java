package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsWallPhotoItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.json.items.PhotosJsonHandler;

public class WallPhotoJsonParser extends AttachmentJsonParser{
    @Override
    public NewsWallPhotoItem parse(JSONObject object, NewsEntityBase value) throws JSONException {
        JSONObject obj = object.getJSONObject("photos");
        //TODO: doing this parse
        PhotosJsonHandler handler = new PhotosJsonHandler();
        Photos photos = handler.parse(obj);
        value.setAttachments(photos);
        return (NewsWallPhotoItem) value;
    }
}
