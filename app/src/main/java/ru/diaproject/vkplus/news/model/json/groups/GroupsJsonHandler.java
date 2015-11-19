package ru.diaproject.vkplus.news.model.json.groups;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import ru.diaproject.vkplus.news.model.groups.Group;

public class GroupsJsonHandler {
    public HashMap<Integer, Group>  parse(JSONArray jsonArray) throws JSONException{
        HashMap<Integer, Group> groups = new HashMap<>();

        for (int index = 0; index < jsonArray.length();index++){
            GroupJsonHandler handler = new GroupJsonHandler();
            Group group = handler.parse(jsonArray.getJSONObject(index));
            groups.put(group.getId(), group);
        }
        return groups;
    }
}
