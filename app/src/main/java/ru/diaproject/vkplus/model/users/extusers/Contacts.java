package ru.diaproject.vkplus.model.users.extusers;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class Contacts extends DataObject {
    public static final String JSON_MOBILE_PHONE = "mobile_phone";
    public static final String JSON_HOME_PHONE = "home_phone";

    public static Contacts parseObject(JSONObject object){
        Contacts contacts = new Contacts();
        try {
            contacts.setHomePhone(object.getString(JSON_HOME_PHONE));
            contacts.setMobilePhone(object.getString(JSON_MOBILE_PHONE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contacts;
    }
    private String mobilePhone;
    private String homePhone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
}
