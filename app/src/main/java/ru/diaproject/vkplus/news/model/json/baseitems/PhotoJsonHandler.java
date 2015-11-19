package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.json.items.PhotosJsonHandler;

public class PhotoJsonHandler extends AttachmentJsonParser {
    @Override
    public NewsPhotoItem parse(JSONObject object, NewsEntityBase value) throws JSONException {
        JSONObject obj = object.getJSONObject("photos");
        PhotosJsonHandler handler = new PhotosJsonHandler();
        Photos photos = handler.parse(obj);
        value.setAttachments(photos);
        return (NewsPhotoItem) value;
    }
}
