package ru.diaproject.vkplus.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.newsitems.DataMainItem;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;
import ru.diaproject.vkplus.model.attachments.Friends;
import ru.diaproject.vkplus.json.items.FriendsJsonHandler;

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
