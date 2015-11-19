package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.items.Notes;
import ru.diaproject.vkplus.news.model.items.NotesInfo;

public class NotesJsonHandler {

    public Notes parse(JSONObject photosJsonObject) throws JSONException {
        Notes notes = new Notes();
        Integer count = photosJsonObject.optInt("count",0);
        notes.setCount(count);

        JSONArray array = photosJsonObject.getJSONArray("items");
        List<NotesInfo> infos = new ArrayList<>();

        for (int cnt = 0; cnt < array.length(); cnt++){
            NotesInfoJsonHandler handler = new NotesInfoJsonHandler();
            NotesInfo info = handler.parse(array.getJSONObject(cnt));
            infos.add(info);
        }
        notes.setNotes(infos);

        return notes;
    }
}
