package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.model.items.Photos;

public class PhotosResponseJsonHandler implements JsonHandler<Photos> {
    private Photos photos;

    public PhotosResponseJsonHandler(Photos photos) {
        this.photos = photos;
    }

    @Override
    public Photos parse(JSONObject object) {
        JSONObject obj;
        try {
            obj = object.getJSONObject("response");
            PhotosJsonHandler handler = new PhotosJsonHandler();
            Photos photos = handler.parse(obj);
            return photos;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Photos();
    }
}
