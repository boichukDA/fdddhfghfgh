package ru.diaproject.vkplus.news.model.groups;

import ru.diaproject.vkplus.news.model.users.DeactivatedType;

public class Group {
    private Integer id;
    private String name;
    private String screenName;
    private CloseType isClosed;
    private DeactivatedType deactivatedType;
    private Boolean isAdmin;
    private AdminLevelType adminLevel;
    private Boolean isMember;
    private GroupType groupType;
    private String photo50;
    private String photo100;
    private String photo200;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public CloseType getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(CloseType isClosed) {
        this.isClosed = isClosed;
    }

    public DeactivatedType getDeactivatedType() {
        return deactivatedType;
    }

    public void setDeactivatedType(DeactivatedType deactivatedType) {
        this.deactivatedType = deactivatedType;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public AdminLevelType getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(AdminLevelType adminLevel) {
        this.adminLevel = adminLevel;
    }

    public Boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public String getPhoto50() {
        return photo50;
    }

    public void setPhoto50(String photo50) {
        this.photo50 = photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }

    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }
}
