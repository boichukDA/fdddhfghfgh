package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.items.CopyHistory;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;

public class CopyHistoryJsonHandler {

    public CopyHistory parse(JSONArray copyHistoryJsonObject) throws JSONException {
        CopyHistory history = new CopyHistory();
        List<CopyHistoryInfo> infoes = new ArrayList<>();

        for (int index = 0; index  < copyHistoryJsonObject.length(); index++) {
            CopyHistoryInfoJsonHandler handler = new CopyHistoryInfoJsonHandler();
            CopyHistoryInfo info = handler.parse(copyHistoryJsonObject.getJSONObject(index));
            infoes.add(info);
        }
        history.setItems(infoes);
        return history;
    }
}
