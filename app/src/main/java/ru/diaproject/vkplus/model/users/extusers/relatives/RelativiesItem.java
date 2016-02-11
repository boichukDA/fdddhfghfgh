package ru.diaproject.vkplus.model.users.extusers.relatives;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class RelativiesItem extends DataObject {
    public static final String JSON_TYPE = "type";
    public static final String JSON_NAME = "name";

    public static RelativiesItem parseObject(JSONObject object){
        RelativiesItem item = new RelativiesItem();

        item.setId(object.optInt(JSON_ID, 0));
        try {
            item.setType(RelativiesType.valueOf(object.getString(JSON_TYPE).toUpperCase()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        item.setName(object.optString(JSON_NAME));
        return item;
    }
    private RelativiesType type;
    private String name;

    public RelativiesType getType() {
        return type;
    }

    public void setType(RelativiesType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
