package ru.diaproject.vkplus.model.items;

import ru.diaproject.vkplus.model.DataObject;

public class RepostsInfo extends DataObject{
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
