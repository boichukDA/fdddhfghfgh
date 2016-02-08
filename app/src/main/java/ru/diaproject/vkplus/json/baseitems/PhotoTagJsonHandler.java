package ru.diaproject.vkplus.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.baseitems.DataMainItem;
import ru.diaproject.vkplus.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.model.items.Photos;
import ru.diaproject.vkplus.json.items.PhotosJsonHandler;

public class PhotoTagJsonHandler extends AttachmentJsonParser{
    @Override
    public IDataMainItem parse(JSONObject object, DataMainItem value) throws JSONException {
        JSONObject obj = object.getJSONObject("photo_tags");
        PhotosJsonHandler handler = new PhotosJsonHandler();
        Photos photos = handler.parse(obj);
        value.setPhotos(photos);
        return value;
    }
}
