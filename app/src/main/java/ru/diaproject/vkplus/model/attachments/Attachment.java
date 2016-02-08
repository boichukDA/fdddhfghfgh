package ru.diaproject.vkplus.model.attachments;

import java.io.Serializable;

import ru.diaproject.vkplus.model.users.IDataObject;

public class Attachment implements Serializable {
    private AttachmentType type;
    private IDataObject item;

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(IDataObject item) {
        this.item = item;
    }
}
