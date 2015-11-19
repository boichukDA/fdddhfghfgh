package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.AlbumInfo;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;
import ru.diaproject.vkplus.news.model.json.items.PhotosInfoJsonHandler;

public class AlbumInfoJsonHandler {
    public AlbumInfo parse(JSONObject jsonObject) throws JSONException {
        AlbumInfo info = new AlbumInfo();
        info.setId(jsonObject.getInt("id"));

        JSONObject photoInfo = jsonObject.optJSONObject("thumb");
        if (photoInfo != null){
            PhotosInfoJsonHandler photosInfoJsonHandler = new PhotosInfoJsonHandler();
            PhotosInfo pInfo = photosInfoJsonHandler.parse(photoInfo);
            info.setThumb(pInfo);
        }

        info.setOwnerId(jsonObject.getInt("owner_id"));
        info.setTitle(jsonObject.getString("title"));
        info.setDescription(jsonObject.getString("description"));
        info.setCreated(jsonObject.getInt("created"));
        info.setUpdated(jsonObject.getInt("updated"));
        info.setSize(jsonObject.getInt("size"));

        return info;
    }
}
