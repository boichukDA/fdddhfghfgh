package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.AppInfo;

public class AppInfoJsonHandler {
    public AppInfo parse(JSONObject jsonObject) throws JSONException {
        AppInfo info = new AppInfo();
        info.setId(jsonObject.getInt("id"));
        info.setName(jsonObject.getString("name"));
        info.setPhoto130(jsonObject.getString("photo_130"));
        info.setPhoto604("photo_604");

        return info;
    }
}
