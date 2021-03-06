package ru.diaproject.vkplus.model.items;

import ru.diaproject.vkplus.model.DataObject;

public class LikesInfo extends DataObject{
    private Integer count;
    private Boolean userLikes;
    private Boolean canLikes;
    private Boolean canPublish;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Boolean userLikes) {
        this.userLikes = userLikes;
    }

    public Boolean getCanLikes() {
        return canLikes;
    }

    public void setCanLikes(Boolean canLikes) {
        this.canLikes = canLikes;
    }

    public Boolean getCanPublish() {
        return canPublish;
    }

    public void setCanPublish(Boolean canPublish) {
        this.canPublish = canPublish;
    }

}
