package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.AttachNoteInfo;

public class AttachNodeInfoJsonHandler {

    public AttachNoteInfo parse(JSONObject jsonObject) throws JSONException {
        AttachNoteInfo info = new AttachNoteInfo();
        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("user_id"));
        info.setTitle(jsonObject.getString("title"));
        info.setText(jsonObject.getString("text"));
        info.setDate(jsonObject.getInt("date"));
        info.setComments(jsonObject.optInt("comments",0));
        info.setReadComments(jsonObject.optInt("read_comments",0));
        info.setViewUrl(jsonObject.optString("view_url",""));
        return info;
    }
}
