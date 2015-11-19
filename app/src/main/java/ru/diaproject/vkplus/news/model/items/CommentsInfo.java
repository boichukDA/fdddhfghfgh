package ru.diaproject.vkplus.news.model.items;

public class CommentsInfo{
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
