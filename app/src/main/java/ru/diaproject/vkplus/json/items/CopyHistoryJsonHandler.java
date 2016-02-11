package ru.diaproject.vkplus.json.items;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.newsitems.copyhistory.CopyHistory;
import ru.diaproject.vkplus.model.newsitems.copyhistory.CopyHistoryInfo;

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
