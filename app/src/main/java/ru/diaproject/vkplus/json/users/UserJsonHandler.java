package ru.diaproject.vkplus.json.users;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.users.DataUser;
import ru.diaproject.vkplus.model.users.DataUserMan;
import ru.diaproject.vkplus.model.users.DataUserWoman;
import ru.diaproject.vkplus.model.users.DeactivatedType;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.model.users.NetworkStatus;

public class UserJsonHandler {
    public IDataUser parse(JSONObject jsonObject) throws JSONException {
        Integer sex = jsonObject.optInt("sex");
        DataUser user;
        if (sex.equals(1))
         user  = new DataUserWoman();
        else  user = new DataUserMan();


        user.setId(jsonObject.getInt("id"));
        user.setFirstName(jsonObject.getString("first_name"));
        user.setLastName(jsonObject.getString("last_name"));
        user.setHidden(jsonObject.optInt("hidden", 0) > 0);

        String deactivated = jsonObject.optString("deactivated", "");
        if (!"".equals(deactivated)){
            DeactivatedType type = DeactivatedType.valueOf(deactivated.toUpperCase());
            user.setType(type);
        }
        else user.setType(DeactivatedType.ACTIVE);

        boolean online = jsonObject.optInt("online")>0;
        boolean mobileOnline = jsonObject.optInt("online_mobile")>0;
        if (online && mobileOnline)
            user.setNetworkStatus(NetworkStatus.ONLINE_MOBILE);
        else if (online)
            user.setNetworkStatus(NetworkStatus.ONLINE);
        else user.setNetworkStatus(NetworkStatus.OFFLINE);

        user.setScreenName(jsonObject.optString("screen_name",""));
        user.setPhoto100(jsonObject.optString("photo_100"));
        user.setStatus(jsonObject.optString("status"));
      return user;
    }
}
