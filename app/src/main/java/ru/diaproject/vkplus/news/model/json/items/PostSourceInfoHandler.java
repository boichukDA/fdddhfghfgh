package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.items.PostSourceInfo;

public class PostSourceInfoHandler {

    public PostSourceInfo parse(JSONObject postSourceObject) throws JSONException {
        PostSourceInfo info = new PostSourceInfo();
        info.setType(postSourceObject.optString("type",""));
        info.setData(postSourceObject.optString("data",""));

        return info;
    }
}
