package ru.diaproject.vkplus.model;

import java.util.List;

import ru.diaproject.vkplus.model.users.IDataObject;

public class ListDataObject<T extends IDataObject> extends DataObject{
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
