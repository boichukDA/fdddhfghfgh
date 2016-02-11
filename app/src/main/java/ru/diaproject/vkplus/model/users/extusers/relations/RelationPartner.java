package ru.diaproject.vkplus.model.users.extusers.relations;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;
import ru.diaproject.vkplus.model.users.extusers.relatives.RelativiesType;

public class RelationPartner extends DataObject{
    public static final String JSON_FNAME = "first_name";
    public static final String JSON_LNAME = "last_name";

    private String firstName;
    private String lastName;

    public static RelationPartner parseObject(JSONObject object){
        RelationPartner item = new RelationPartner();

        try {
            item.setId(object.getInt(JSON_ID));
            item.setFirstName(object.getString(JSON_FNAME));
            item.setLastName(object.getString(JSON_LNAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
