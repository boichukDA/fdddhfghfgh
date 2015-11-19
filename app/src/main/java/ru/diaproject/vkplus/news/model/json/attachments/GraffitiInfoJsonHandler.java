package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.GraffitiInfo;


public class GraffitiInfoJsonHandler {

    public GraffitiInfo parse(JSONObject jsonObject) throws JSONException {
        GraffitiInfo info = new GraffitiInfo();
        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("owner_id"));

        info.setPhoto200(jsonObject.optString("photo_200", ""));

        info.setPhoto586(jsonObject.optString("photo_586",""));
        return info;
    }
}
