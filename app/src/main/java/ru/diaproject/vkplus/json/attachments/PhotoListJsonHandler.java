package ru.diaproject.vkplus.json.attachments;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.attachments.PhotoListInfo;

public class PhotoListJsonHandler {

    public PhotoListInfo parse(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();

        for (int index = 0; index < jsonArray.length(); index++){
            list.add(jsonArray.getString(index));
        }
        PhotoListInfo info = new PhotoListInfo();
        info.setPhotoList(list);
        return info;
    }
}
