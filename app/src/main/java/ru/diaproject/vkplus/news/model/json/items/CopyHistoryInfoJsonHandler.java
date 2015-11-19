package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.Attachments;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;
import ru.diaproject.vkplus.news.model.json.attachments.AttachmentsJsonHandler;

public class CopyHistoryInfoJsonHandler {
    public CopyHistoryInfo parse(JSONObject jsonObject) throws JSONException {
        CopyHistoryInfo info = new CopyHistoryInfo();
        Integer id = jsonObject.optInt("id", 0);
        info.setId(id);

        Integer ownerId = jsonObject.optInt("owner_id", 0);
        info.setOwnerId(ownerId);

        Integer fromId = jsonObject.optInt("from_id", 0);
        info.setFromId(fromId);

        Integer date= jsonObject.optInt("date", 0);
        info.setDate(date);

        /*try {
            PostType type = PostType.valueOf(jsonObject.optString("post_type", "").toUpperCase());
            info.setPostType(type);
        }
        catch(Exception e){
            e.printStackTrace();
        }*/
        String text = jsonObject.optString("text", "");
        info.setText(text);

        JSONArray attachments = jsonObject.optJSONArray("attachments");
        if (attachments!=null) {
            AttachmentsJsonHandler attHandler = new AttachmentsJsonHandler();
            Attachments attachms = attHandler.parse(attachments);
            info.setAttachments(attachms);
        }
        return info;
    }
}
