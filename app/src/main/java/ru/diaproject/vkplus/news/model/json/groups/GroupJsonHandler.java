package ru.diaproject.vkplus.news.model.json.groups;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.groups.AdminLevelType;
import ru.diaproject.vkplus.news.model.groups.CloseType;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.groups.GroupType;
import ru.diaproject.vkplus.news.model.users.DeactivatedType;

public class GroupJsonHandler {
    public Group parse(JSONObject object) throws JSONException{
        Group group = new Group();
        group.setId(object.getInt("id"));
        group.setName(object.getString("name"));
        group.setScreenName(object.getString("screen_name"));
        group.setIsClosed(CloseType.valueOf(object.getInt("is_closed")));

        String deacSring = object.optString("deactivated","");
        if(!"".equals(deacSring)){
            DeactivatedType type = DeactivatedType.valueOf(deacSring.toUpperCase());
            group.setDeactivatedType(type);
        }else group.setDeactivatedType(DeactivatedType.ACTIVE);

        group.setIsAdmin(object.optInt("is_admin",0)>0?true:false);
        if (group.getIsAdmin()){
            Integer adminLevel = object.getInt("admin_level");
            AdminLevelType type = AdminLevelType.valueOf(adminLevel);
            group.setAdminLevel(type);
        }
        else group.setAdminLevel(AdminLevelType.NOT_ADMIN);

        group.setIsMember(object.optInt("is_member")>0?true:false);
        group.setGroupType(GroupType.valueOf(object.getString("type").toUpperCase()));

        group.setPhoto50(object.getString("photo_50"));
        group.setPhoto100(object.getString("photo_100"));
        group.setPhoto200(object.getString("photo_200"));

        return group;
    }
}
