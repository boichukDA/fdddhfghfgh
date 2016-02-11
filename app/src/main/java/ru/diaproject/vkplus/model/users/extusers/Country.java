package ru.diaproject.vkplus.model.users.extusers;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class Country extends DataObject {
    public static final String JSON_TITLE = "title";

    public static Country parseObject(JSONObject object){
        Country country = new Country();
        try {
            country.setId(Integer.parseInt(object.getString(JSON_ID)));
            country.setTitle(object.getString(JSON_TITLE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return country;
    }
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
