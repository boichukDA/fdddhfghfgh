package ru.diaproject.vkplus.model.attachments;

import ru.diaproject.vkplus.model.DataObject;

public class PostSourceInfo extends DataObject{
    private String type ="";
    private String data = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
