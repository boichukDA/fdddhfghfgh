package ru.diaproject.vkplus.news.model.items;

public class RepostsInfo {
    private Integer count;
    private Boolean userReposted;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getUserReposted() {
        return userReposted;
    }

    public void setUserReposted(Boolean userReposted) {
        this.userReposted = userReposted;
    }
}
