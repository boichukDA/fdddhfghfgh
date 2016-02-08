package ru.diaproject.vkplus.json.items;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.items.CommentsInfo;

public class CommentsInfoJsonHandler{
    public CommentsInfo parse(JSONObject jsonObject) throws JSONException {
        CommentsInfo info = new CommentsInfo();

        info.setCount(jsonObject.optInt("count",0));
        info.setCanPost(jsonObject.optInt("can_post",0)>0);
        return  info;
    }
}
