package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class PhotoListJsonHandler {

    public List<String> parse(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();

        for (int index = 0; index < jsonArray.length(); index++){
            list.add(jsonArray.getString(index));
        }

        return list;
    }
}
