package ru.diaproject.vkplus.model.items;

import ru.diaproject.vkplus.model.DataObject;

public class CommentsInfo extends DataObject {
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
