package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsFriendItem;
import ru.diaproject.vkplus.news.model.items.Friends;
import ru.diaproject.vkplus.news.model.json.items.FriendsJsonHandler;

public class FriendJsonHandler extends AttachmentJsonParser {
    @Override
    public NewsFriendItem parse(JSONObject object, NewsEntityBase value) throws JSONException {
        JSONObject obj = object.getJSONObject("friends");
        FriendsJsonHandler handler = new FriendsJsonHandler();
        Friends friends = handler.parse(obj);
        value.setAttachments(friends);
        return (NewsFriendItem) value;
    }
}
