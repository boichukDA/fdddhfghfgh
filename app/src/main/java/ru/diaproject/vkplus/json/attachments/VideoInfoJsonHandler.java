package ru.diaproject.vkplus.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.attachments.VideoInfo;

public class VideoInfoJsonHandler {
    public VideoInfo parse(JSONObject jsonObject) throws JSONException {
        VideoInfo info = new VideoInfo();

        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("owner_id"));
        info.setDate(jsonObject.getInt("date"));
        info.setAddingDate(jsonObject.optInt("adding_date", 0));

        info.setTitle(jsonObject.optString("title", ""));
        info.setDescription(jsonObject.optString("description", ""));
        info.setDuration(jsonObject.optInt("duration", 0));
        info.setPhoto130(jsonObject.getString("photo_130"));

        info.setPhoto320(jsonObject.getString("photo_320"));
        info.setPhoto640(jsonObject.optString("photo_640", ""));
        info.setViews(jsonObject.optInt("views",0));
        info.setComments(jsonObject.optInt("comments",0));

        info.setPlayer(jsonObject.optString("player",""));
        info.setAccessKey(jsonObject.optString("access_key",""));
        info.setProcessing(jsonObject.optInt("processing",0)>0?true:false);

        return info;
    }
}
