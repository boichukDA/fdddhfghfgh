package ru.diaproject.vkplus.news.model.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.groups.IDataGroup;
import ru.diaproject.vkplus.news.model.json.groups.GroupsJsonHandler;
import ru.diaproject.vkplus.news.model.json.items.ItemsJsonHandler;
import ru.diaproject.vkplus.news.model.json.users.UsersJsonHandler;
import ru.diaproject.vkplus.news.model.users.IDataUser;

public class ResponseJsonHandler implements JsonHandler{
    public ResponseJsonHandler() {
    }

    @Override
    public NewsResponse parse(JSONObject object) {
        NewsResponse response = new NewsResponse();
        try {
            JSONObject respObject = object.getJSONObject("response");

            JSONArray itemsObject = respObject.getJSONArray("items");
            ItemsJsonHandler itemsJsonHandler = new ItemsJsonHandler();
            List<IDataMainItem> items = itemsJsonHandler.parse(itemsObject);
            response.setItems(items);

            JSONArray profilesObject = respObject.getJSONArray("profiles");
            UsersJsonHandler usersJsonHandler = new UsersJsonHandler();
            HashMap<Integer, IDataUser> users = usersJsonHandler.parse(profilesObject);
            response.setProfiles(users);

            JSONArray groupsObject = respObject.getJSONArray("groups");
            GroupsJsonHandler groupsJsonHandler = new GroupsJsonHandler();
           HashMap<Integer, IDataGroup> groups = groupsJsonHandler.parse(groupsObject);
            response.setGroups(groups);

            response.setNextFrom(respObject.optString("next_from",""));
        } catch (JSONException e) {
           Log.e("JSON Parsing", "exception", e);
        }
        return response;
    }
}
