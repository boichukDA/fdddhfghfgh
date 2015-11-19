package ru.diaproject.vkplus.news.model.json.users;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.users.DeactivatedType;
import ru.diaproject.vkplus.news.model.users.User;

public class UserJsonHandler {
    public User parse(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setId(jsonObject.getInt("id"));
        user.setFirstName(jsonObject.getString("first_name"));
        user.setLastName(jsonObject.getString("last_name"));
        user.setHidden(jsonObject.optInt("hidden", 0) > 0 ? true : false);

        String deactivated = jsonObject.optString("deactivated", "");
        if (!"".equals(deactivated)){
            DeactivatedType type = DeactivatedType.valueOf(deactivated.toUpperCase());
            user.setType(type);
        }
        else user.setType(DeactivatedType.ACTIVE);

        user.setSex(new Integer(jsonObject.optInt("sex")).byteValue());
        user.setOnline(jsonObject.optInt("online")>0?true:false);
        user.setOnlineMobile(jsonObject.optInt("online_mobile")>0?true:false);
        user.setScreenName(jsonObject.optString("screen_name",""));
        user.setPhoto50(jsonObject.optString("photo_50"));
        user.setPhoto100(jsonObject.optString("photo_100"));
        user.setStatus(jsonObject.optString("status"));
      return user;
    }
}
