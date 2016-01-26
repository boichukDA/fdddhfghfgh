package ru.diaproject.vkplus.news.model.groups;


import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.VKPlusApplication;
import ru.diaproject.vkplus.core.utils.VkStringUtils;
import ru.diaproject.vkplus.news.model.users.DataOwner;
import ru.diaproject.vkplus.news.model.users.DeactivatedType;
import ru.diaproject.vkplus.news.model.users.OwnerSex;

public class DataGroup extends DataOwner implements IDataGroup{
    private String name;
    private String screenName;
    private CloseType isClosed;
    private DeactivatedType deactivatedType;
    private Boolean isAdmin;
    private AdminLevelType adminLevel;
    private Boolean isMember;
    private GroupType groupType;
    private String photo200;

    @Override
    public OwnerSex getSex() {
        return OwnerSex.MAN;
    }

    @Override
    public Integer getPlaceholderResource() {
        return R.drawable.group_silhouette;
    }

    @Override
    public Integer getErrorResource() {
        return R.drawable.group_silhouette;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public CloseType getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(CloseType isClosed) {
        this.isClosed = isClosed;
    }

    @Override
    public DeactivatedType getDeactivatedType() {
        return deactivatedType;
    }

    public void setDeactivatedType(DeactivatedType deactivatedType) {
        this.deactivatedType = deactivatedType;
    }

    @Override
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public AdminLevelType getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(AdminLevelType adminLevel) {
        this.adminLevel = adminLevel;
    }

    @Override
    public Boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(Boolean isMember) {
        this.isMember = isMember;
    }

    @Override
    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    @Override
    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }

    @Override
    public void prepareItems() {
        setFullName(VkStringUtils.prepareTextToVkName(name, VKPlusApplication.getStaticContext()));
    }
}
