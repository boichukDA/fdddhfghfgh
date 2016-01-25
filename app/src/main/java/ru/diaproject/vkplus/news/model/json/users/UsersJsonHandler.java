package ru.diaproject.vkplus.news.model.json.users;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import ru.diaproject.vkplus.news.model.users.IDataUser;

public class UsersJsonHandler {
    public HashMap<Integer, IDataUser> parse(JSONArray jsonArray) throws JSONException {
        HashMap<Integer, IDataUser> users = new HashMap<>();

        for (int index = 0; index < jsonArray.length();index++){
            UserJsonHandler handler = new UserJsonHandler();
            IDataUser user = handler.parse(jsonArray.getJSONObject(index));
            users.put(user.getId(), user);
        }
        return users;
    }
}
