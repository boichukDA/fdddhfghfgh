package ru.diaproject.vkplus.json.items;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.items.LikesInfo;

public class LikesInfoJsonHandler {
    public LikesInfo parse(JSONObject jsonObject) throws JSONException {
        LikesInfo info = new LikesInfo();

        info.setCount(jsonObject.optInt("count", 0));
        info.setCanLikes(jsonObject.optInt("can_like",0)>0);
        info.setCanPublish(jsonObject.optInt("can_publish",0)>0);
        info.setUserLikes(jsonObject.optInt("user_likes",0)>0);
        return  info;
    }
}
