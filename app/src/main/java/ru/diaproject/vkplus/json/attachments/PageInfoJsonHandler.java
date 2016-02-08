package ru.diaproject.vkplus.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.attachments.PageInfo;

public class PageInfoJsonHandler {

    public PageInfo parse(JSONObject jsonObject) throws JSONException {
        PageInfo info = new PageInfo();
        info.setId(jsonObject.getInt("id"));
        info.setGroupId(jsonObject.getInt("group_id"));
        info.setCreatorId(jsonObject.optInt("creator_id"));
        info.setTitle(jsonObject.getString("title"));
        info.setCurrentUserCanEdit(jsonObject.optInt("current_user_can_edit", 0) > 0 ? true : false);
        info.setCurrentUserCanEditAccess(jsonObject.optInt("current_user_can_edit_access", 0) > 0 ? true : false);
        info.setWhoCanView(new Integer(jsonObject.optInt("who_can_view",0)).byteValue());
        info.setWhoCanEdit(new Integer(jsonObject.optInt("who_can_edit",0)).byteValue());
        info.setEdited(jsonObject.getInt("edited"));
        info.setCreated(jsonObject.getInt("created"));
        info.setEditorId(jsonObject.optInt("editor_id"));
        info.setViews(jsonObject.getInt("views"));
        info.setParent(jsonObject.optString("parent",""));
        info.setParent2(jsonObject.optString("parent2",""));
        info.setHtml(jsonObject.optString("html",""));
        info.setSource(jsonObject.optString("source",""));
        info.setViewUrl(jsonObject.optString("view_url"));
        return info;
    }
}
