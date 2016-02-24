package ru.diaproject.vkplus.model.users.extusers.relatives;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;
import ru.diaproject.vkplus.model.users.IDataUser;

public class Relative extends DataObject {
    public static final String JSON_TYPE = "type";
    public static final String JSON_NAME = "name";

    public static Relative parseObject(JSONObject object){
        Relative item = new Relative();

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
    private Integer startSpan;
    private Integer endSpan;
    private IDataUser user;

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

    public Integer getStartSpan() {
        return startSpan;
    }

    public void setStartSpan(Integer startSpan) {
        this.startSpan = startSpan;
    }

    public Integer getEndSpan() {
        return endSpan;
    }

    public void setEndSpan(Integer endSpan) {
        this.endSpan = endSpan;
    }

    public IDataUser getUser() {
        return user;
    }

    public void setUser(IDataUser user) {
        this.user = user;
    }
}
