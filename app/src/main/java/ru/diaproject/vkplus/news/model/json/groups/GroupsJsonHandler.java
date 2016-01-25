package ru.diaproject.vkplus.news.model.json.groups;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import ru.diaproject.vkplus.news.model.groups.IDataGroup;


public class GroupsJsonHandler {
    public HashMap<Integer, IDataGroup>  parse(JSONArray jsonArray) throws JSONException{
        HashMap<Integer, IDataGroup> groups = new HashMap<>();

        for (int index = 0; index < jsonArray.length();index++){
            GroupJsonHandler handler = new GroupJsonHandler();
            IDataGroup group = handler.parse(jsonArray.getJSONObject(index));
            groups.put(group.getId(), group);
        }
        return groups;
    }
}
