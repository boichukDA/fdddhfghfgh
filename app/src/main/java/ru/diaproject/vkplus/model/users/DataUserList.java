package ru.diaproject.vkplus.model.users;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.ListDataObject;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;

public class DataUserList extends ListDataObject<DataUserExt> {
    public static final String JSON_RESPONSE = "response";

    public static DataUserList parseObject(JSONObject object){
        DataUserList list = new DataUserList();
        List<DataUserExt> users = new ArrayList<>();
        JSONArray array = object.optJSONArray(JSON_RESPONSE);

        if (array != null){
            for (int index = 0; index < array.length(); index++) {
                JSONObject currentUser = array.optJSONObject(index);
                if (currentUser != null)
                    users.add(DataUserExt.parseObject(currentUser));
            }
        }
        list.setItems(users);
        return list;
    }
}
