package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.items.NotesInfo;

public class NotesInfoJsonHandler {
    public NotesInfo parse(JSONObject photosJsonObject) throws JSONException {
        NotesInfo info = new NotesInfo();

        Integer id = photosJsonObject.optInt("id", 0);
        info.setId(id);

        Integer ownerId = photosJsonObject.optInt("owner_id", 0);
        info.setOwnerId(ownerId);


        String title = photosJsonObject.getString("title");
        info.setTitle(title);

        Integer comments = photosJsonObject.optInt("comments", 0);
        info.setComments(comments);

        return info;
    }
}
