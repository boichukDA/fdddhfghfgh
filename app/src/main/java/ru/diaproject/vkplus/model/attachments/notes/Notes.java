package ru.diaproject.vkplus.model.attachments.notes;

import java.util.List;

import ru.diaproject.vkplus.model.DataObject;

public class Notes extends DataObject {
    private Integer count;
    private List<NotesInfo> notes;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<NotesInfo> getNotes() {
        return notes;
    }

    public void setNotes(List<NotesInfo> notes) {
        this.notes = notes;
    }
}
