package ru.diaproject.vkplus.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.attachments.PostedPhotoInfo;

public class PostedPhotoInfoJsonHandler {
    public PostedPhotoInfo parse(JSONObject jsonObject) throws JSONException {
        PostedPhotoInfo info = new PostedPhotoInfo();

        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("owner_id"));
        info.setPhoto130(jsonObject.getString("photo_130"));
        info.setPhoto604(jsonObject.optString("photo_604",""));

        return info;
    }
}
