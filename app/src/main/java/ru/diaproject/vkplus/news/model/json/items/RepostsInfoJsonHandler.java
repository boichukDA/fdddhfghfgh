package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.items.RepostsInfo;

public class RepostsInfoJsonHandler {
    public RepostsInfo parse(JSONObject jsonObject) throws JSONException {
        RepostsInfo info = new RepostsInfo();

        info.setCount(jsonObject.optInt("count", 0));
        info.setUserReposted(jsonObject.optInt("user_reposted",0)>0?true:false);
        return  info;
    }
}
