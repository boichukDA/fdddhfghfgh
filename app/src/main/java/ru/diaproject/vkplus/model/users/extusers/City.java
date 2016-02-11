package ru.diaproject.vkplus.model.users.extusers;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class City extends DataObject{
    private static final String JSON_TITLE = "title";
    public static City parseObject(JSONObject object){
        City city = new City();
        try {
            city.setId(Integer.parseInt(object.getString(JSON_ID)));
            city.setTitle(object.getString(JSON_TITLE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return city;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
