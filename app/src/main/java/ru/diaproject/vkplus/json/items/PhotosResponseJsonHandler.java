package ru.diaproject.vkplus.json.items;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.model.attachments.photos.PhotosInfo;

public class PhotosResponseJsonHandler implements JsonHandler, Serializable {

    public PhotosResponseJsonHandler() {
    }

    @Override
    public Photos parse(JSONObject object) {
        JSONObject obj;
        obj = object.optJSONObject("response");
        if (obj != null)
           return parserObject(obj);
        else {
            JSONArray array = object.optJSONArray("response");
            if( array != null){
               return parserArray(array);
            }
            else new Photos();
        }
        return new Photos();
    }
    public Photos parserObject(JSONObject obj){
        try {
        PhotosJsonHandler handler = new PhotosJsonHandler();
        Photos photos = handler.parse(obj);
        return photos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Photos();
    }

    public Photos parserArray(JSONArray array){
        Log.e("Parse photos array", "exception");
        Photos photos = new Photos();
        List<PhotosInfo> infos = new ArrayList<>();

        for (int size = 0; size < array.length();size++) {
            try {
                JSONObject tempObject = array.getJSONObject(size);
                PhotosInfo info = (new PhotosInfoJsonHandler()).parse(tempObject);
                infos.add(info);
            } catch (Throwable e) {
                Log.e("Parse error", "exception", e);
            }
        }

        photos.setPhotos(infos);
        photos.setCount(infos.size());

        return photos;
    }
}
