package ru.diaproject.vkplus.model.attachments;

import ru.diaproject.vkplus.model.DataObject;

public class AppInfo extends DataObject {
    private String name;
    private String photo130;
    private String photo604;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    public String getPhoto604() {
        return photo604;
    }

    public void setPhoto604(String photo604) {
        this.photo604 = photo604;
    }
}
