package ru.diaproject.vkplus.model.users.extusers.occupations;


import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class Occupation extends DataObject{
    public static final String JSON_TYPE = "type";
    public static final String JSON_NAME = "name";

    public static  Occupation parseObject(JSONObject object){
        Occupation occupation = new Occupation();
        occupation.setType(OccupationType.valueOf(object.optString(JSON_TYPE).toUpperCase()));
        occupation.setName(object.optString(JSON_NAME));
        return occupation;
    }

    private OccupationType type;
    private String name;

    public OccupationType getType() {
        return type;
    }

    public void setType(OccupationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
