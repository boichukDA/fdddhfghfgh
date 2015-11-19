package ru.diaproject.vkplus.news.model.json.users;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import ru.diaproject.vkplus.news.model.users.User;

public class UsersJsonHandler {
    public HashMap<Integer, User> parse(JSONArray jsonArray) throws JSONException {
        HashMap<Integer, User> users = new HashMap<>();

        for (int index = 0; index < jsonArray.length();index++){
            UserJsonHandler handler = new UserJsonHandler();
            User user = handler.parse(jsonArray.getJSONObject(index));
            users.put(user.getId(), user);
        }
        return users;
    }
}
