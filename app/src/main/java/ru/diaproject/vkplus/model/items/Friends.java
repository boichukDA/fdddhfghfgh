package ru.diaproject.vkplus.model.items;

import java.util.List;

import ru.diaproject.vkplus.model.DataObject;

public class Friends extends DataObject {
    private Integer count;
    private List<Integer> friends;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Integer> getFriends() {
        return friends;
    }

    public void setFriends(List<Integer> friends) {
        this.friends = friends;
    }
}
