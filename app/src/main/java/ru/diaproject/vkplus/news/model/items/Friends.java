package ru.diaproject.vkplus.news.model.items;

import java.util.List;

public class Friends {
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
