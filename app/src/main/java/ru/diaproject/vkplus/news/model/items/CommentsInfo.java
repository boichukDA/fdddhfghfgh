package ru.diaproject.vkplus.news.model.items;

import java.io.Serializable;

public class CommentsInfo implements Serializable{
    private Integer count;
    private Boolean canPost;

    public Boolean getCanPost() {
        return canPost;
    }

    public void setCanPost(Boolean canPost) {
        this.canPost = canPost;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
