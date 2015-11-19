package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoTagItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.json.items.PhotosJsonHandler;

public class PhotoTagJsonHandler extends AttachmentJsonParser{
    @Override
    public NewsPhotoTagItem parse(JSONObject object, NewsEntityBase value) throws JSONException {
        JSONObject obj = object.getJSONObject("photo_tags");
        PhotosJsonHandler handler = new PhotosJsonHandler();
        Photos photos = handler.parse(obj);
        value.setAttachments(photos);
        return (NewsPhotoTagItem) value;
    }
}