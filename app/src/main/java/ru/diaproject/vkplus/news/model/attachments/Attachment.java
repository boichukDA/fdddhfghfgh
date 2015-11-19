package ru.diaproject.vkplus.news.model.attachments;

public class Attachment {
    private AttachmentType type;
    private  Object item;

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}
