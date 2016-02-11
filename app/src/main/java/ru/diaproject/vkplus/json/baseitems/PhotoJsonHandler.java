package ru.diaproject.vkplus.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.newsitems.DataMainItem;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.json.items.PhotosJsonHandler;

public class PhotoJsonHandler extends AttachmentJsonParser {
    @Override
    public IDataMainItem parse(JSONObject object, DataMainItem value) throws JSONException {
        JSONObject obj = object.getJSONObject("photos");
        PhotosJsonHandler handler = new PhotosJsonHandler();
        Photos photos = handler.parse(obj);
        value.setPhotos(photos);
        return value;
    }
}
