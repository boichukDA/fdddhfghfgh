package ru.diaproject.vkplus.news.model.users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.model.json.users.UserJsonHandler;

public class UserResponseJsonHandler implements JsonHandler<User> {
    private User user;

    public UserResponseJsonHandler(User user){
        this.user = user;
    }
    @Override
    public User parse(JSONObject jsonObject) {
        try {
            JSONArray obj = jsonObject.getJSONArray("response");
            JSONObject json = obj.getJSONObject(0);
            UserJsonHandler handler = new UserJsonHandler();
            user = handler.parse(json);
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
