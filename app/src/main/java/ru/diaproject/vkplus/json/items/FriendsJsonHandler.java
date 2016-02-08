package ru.diaproject.vkplus.json.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.items.Friends;

public class FriendsJsonHandler {

    public Friends parse(JSONObject photosJsonObject) throws JSONException {
        Friends friends = new Friends();
        Integer count = photosJsonObject.optInt("count",0);
        friends.setCount(count);

        JSONArray array = photosJsonObject.getJSONArray("items");
        List<Integer> fries = new ArrayList<>();

        for (int cnt = 0; cnt < array.length(); cnt++){
            fries.add(array.getJSONObject(cnt).getInt("user_id"));
        }
        friends.setFriends(fries);

        return friends;
    }
}
