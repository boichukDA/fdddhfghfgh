package ru.diaproject.vkplus.news.model.items;

import ru.diaproject.vkplus.news.model.attachments.Attachments;
import ru.diaproject.vkplus.news.model.baseitems.PostType;

public class CopyHistoryInfo {
    private Integer id;
    private Integer ownerId;
    private Integer fromId;
    private Integer date;
    private PostType postType;
    private String text;
    private Attachments attachments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    public boolean containsPhoto() {
        return attachments!=null && attachments.containsPhoto();
    }

    public boolean containsVideo() {
        return attachments!=null && attachments.containsVideo();
    }

    public boolean containsAudio() {
        return attachments!=null && attachments.containsAudio();
    }
}
