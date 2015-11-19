package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.DocInfo;


public class DocInfoJsonHandler {

    public DocInfo parse(JSONObject jsonObject) throws JSONException {
        DocInfo info = new DocInfo();
        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("owner_id"));
        info.setSize(jsonObject.optInt("size", 0));

        info.setUrl(jsonObject.getString("url"));
        info.setExt(jsonObject.getString("ext"));
        info.setTitle(jsonObject.optString("title", ""));
        info.setPhoto100(jsonObject.optString("photo_100", ""));

        info.setPhoto130(jsonObject.optString("photo_130",""));
        return info;
    }
}
