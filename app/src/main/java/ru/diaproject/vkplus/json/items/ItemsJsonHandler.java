package ru.diaproject.vkplus.json.items;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.json.baseitems.BaseItemJsonHandler;

public class ItemsJsonHandler {

    public List<IDataMainItem> parse(JSONArray itemsJsonObject) throws JSONException {
        List<IDataMainItem> items = new ArrayList<>();

        for (int size = 0; size < itemsJsonObject.length();size++) {
            try {
                JSONObject tempObject = itemsJsonObject.getJSONObject(size);
                BaseItemJsonHandler itemJsonHandler = new BaseItemJsonHandler();
                IDataMainItem entity = itemJsonHandler.parse(tempObject);
                items.add(entity);
            } catch(Throwable e){
                Log.e("Parse error","exception", e);
            }
        }
        return items;
    }
}
