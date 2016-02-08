package ru.diaproject.vkplus.model.users;

import ru.diaproject.vkplus.VKPlusApplication;
import ru.diaproject.vkplus.core.utils.VkStringUtils;

public abstract class  DataUser extends DataOwner implements IDataObject, IDataUser {
    private String firstName;
    private String lastName;
    private DeactivatedType type;
    private boolean hidden;
    private String screenName;
    private NetworkStatus networkStatus;
    private String status;

    public DataUser(){
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public DeactivatedType getType() {
        return type;
    }

    public void setType(DeactivatedType type) {
        this.type = type;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public NetworkStatus getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(NetworkStatus networkStatus) {
        this.networkStatus = networkStatus;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void prepareItems() {
        setFullName(VkStringUtils.prepareTextToVkName(firstName + " " + lastName, VKPlusApplication.getStaticContext()));
    }
}
