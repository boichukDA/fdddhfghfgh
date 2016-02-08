package ru.diaproject.vkplus.model.items;

import ru.diaproject.vkplus.model.baseitems.DataItem;

public class NotesInfo extends DataItem {
    private String title;
    private Integer comments;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
