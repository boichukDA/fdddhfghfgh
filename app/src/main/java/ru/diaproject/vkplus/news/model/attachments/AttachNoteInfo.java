package ru.diaproject.vkplus.news.model.attachments;

import java.io.Serializable;

import ru.diaproject.vkplus.news.model.baseitems.DataItem;

public class AttachNoteInfo extends DataItem {
    private String title;
    private String text;
    private Integer date;
    private Integer comments;
    private Integer readComments;
    private String viewUrl;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String tect) {
        this.text = tect;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getReadComments() {
        return readComments;
    }

    public void setReadComments(Integer readComments) {
        this.readComments = readComments;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }
}
