package ru.diaproject.vkplus.news.model.json.users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.model.IDataResult;
import ru.diaproject.vkplus.news.model.users.IDataUser;

public class UserResponseJsonHandler implements JsonHandler {

    public UserResponseJsonHandler(){
    }

    @Override
    public IDataUser parse(JSONObject jsonObject) {
        try {
            JSONArray obj = jsonObject.getJSONArray("response");
            JSONObject json = obj.getJSONObject(0);
            UserJsonHandler handler = new UserJsonHandler();
            IDataUser user = handler.parse(json);
            return user;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
