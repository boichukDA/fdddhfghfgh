package ru.diaproject.vkplus.json.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.items.Photos;
import ru.diaproject.vkplus.model.items.PhotosInfo;

public class PhotosJsonHandler {

    public Photos parse(JSONObject photosJsonObject) throws JSONException {
        Photos photos = new Photos();
        Integer count = photosJsonObject.optInt("count",0);
        photos.setCount(count);

        JSONArray array = photosJsonObject.getJSONArray("items");
        List<PhotosInfo> infos = new ArrayList<>();

        for (int cnt = 0; cnt < array.length(); cnt++){
            PhotosInfoJsonHandler handler = new PhotosInfoJsonHandler();
            PhotosInfo info = handler.parse(array.getJSONObject(cnt));
            infos.add(info);
        }
        photos.setPhotos(infos);
        return photos;
    }
}
