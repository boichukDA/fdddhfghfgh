package ru.diaproject.vkplus.news.model.attachments;

import ru.diaproject.vkplus.news.model.baseitems.DataItem;

public class GraffitiInfo extends DataItem {
    private String photo200;
    private String photo586;

    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }

    public String getPhoto586() {
        return photo586;
    }

    public void setPhoto586(String photo586) {
        this.photo586 = photo586;
    }
}
