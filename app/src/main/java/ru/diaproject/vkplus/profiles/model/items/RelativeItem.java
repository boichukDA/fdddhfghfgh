package ru.diaproject.vkplus.profiles.model.items;


import java.util.List;

import ru.diaproject.vkplus.model.users.extusers.relatives.Relative;

public class RelativeItem extends KeyValueItem{
    private List<Relative> items;

    public List<Relative> getItems() {
        return items;
    }

    public void setItems(List<Relative> items) {
        this.items = items;
    }
}
