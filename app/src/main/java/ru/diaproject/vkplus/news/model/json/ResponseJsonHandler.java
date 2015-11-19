package ru.diaproject.vkplus.news.model.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.json.groups.GroupsJsonHandler;
import ru.diaproject.vkplus.news.model.json.items.ItemsJsonHandler;
import ru.diaproject.vkplus.news.model.json.users.UsersJsonHandler;
import ru.diaproject.vkplus.news.model.users.User;

public class ResponseJsonHandler implements JsonHandler<Response>{
    private Response response;
    public ResponseJsonHandler(Response response) {
        this.response = response;
    }

    @Override
    public Response parse(JSONObject object) {
        try {
            JSONObject respObject = object.getJSONObject("response");

            JSONArray itemsObject = respObject.getJSONArray("items");
            ItemsJsonHandler itemsJsonHandler = new ItemsJsonHandler();
            List<NewsEntityBase> items = itemsJsonHandler.parse(itemsObject);
            response.setItems(items);

            JSONArray profilesObject = respObject.getJSONArray("profiles");
            UsersJsonHandler usersJsonHandler = new UsersJsonHandler();
            HashMap<Integer, User> users = usersJsonHandler.parse(profilesObject);
            response.setProfiles(users);

            JSONArray groupsObject = respObject.getJSONArray("groups");
            GroupsJsonHandler groupsJsonHandler = new GroupsJsonHandler();
           HashMap<Integer, Group> groups = groupsJsonHandler.parse(groupsObject);
            response.setGroups(groups);

            response.setNextFrom(respObject.optString("next_from",""));
        } catch (JSONException e) {
           Log.e("JSON Parsing", "exception", e);
        }
        return response;
    }
}
