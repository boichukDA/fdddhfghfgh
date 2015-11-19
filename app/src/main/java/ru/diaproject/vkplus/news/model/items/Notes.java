package ru.diaproject.vkplus.news.model.items;

import java.util.List;

public class Notes {
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
