package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.DataMainItem;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.items.Friends;
import ru.diaproject.vkplus.news.model.json.items.FriendsJsonHandler;

public class FriendJsonHandler extends AttachmentJsonParser {
    @Override
    public IDataMainItem parse(JSONObject object, DataMainItem value) throws JSONException {
        JSONObject obj = object.getJSONObject("friends");
        FriendsJsonHandler handler = new FriendsJsonHandler();
        Friends friends = handler.parse(obj);
        value.setFriends(friends);
        return value;
    }
}
