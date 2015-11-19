package ru.diaproject.vkplus.vkcore;

import ru.diaproject.vkplus.vkcore.persistence.IPersistenceWorker;
import ru.diaproject.vkplus.vkcore.persistence.VKPersistenceWorker;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public enum VK {
    SINGLETON;

    private final Object[] secrets = new Object[]{ 5007122, "FhrsAU2IimyEd1lve90Q"};

    private final String VK_REDIRECT_URL  = "http://oauth.vk.com/blank.html";
    private final String AUTHORIZATION_URL = "http://oauth.vk.com/oauth/authorize?v=5.35&client_id=%s&scope=wall,friends,photos,offline,nohttps&redirect_uri=http://oauth.vk.com/blank.html&display=mobile&response_type=token";
    private final String VK_ICON_URL = "http://vk.com/images/emoji/";
    private VKUser user;
    private VKConfiguration vkConfig;
    private IPersistenceWorker worker;

    VK(){
        worker = new VKPersistenceWorker();
        user = worker.tryToRestoreObject(VKUser.class);
        vkConfig = worker.tryToRestoreObject(VKConfiguration.class);
    }
    public String getRedirectURl(){
        return  VK_REDIRECT_URL;
    }

    public void setUser(VKUser user) {
        worker.saveObject(user);
        this.user = user;

    }

    public VKUser getUser() {
        return user;
    }

    public String getAuthorizationUrl() {
        return String.format(AUTHORIZATION_URL, getAppId());
    }
    public Integer getAppId(){
        return (Integer) secrets[0];
    }

    public String getSecretKey(){
        return (String) secrets[1];
    }

    public boolean isAuthorization() {
        return VK.SINGLETON.getUser() == null
                || VK.SINGLETON.getUser().getConfiguration().getAccessToken() == null
                || "".equals(VK.SINGLETON.getUser().getConfiguration().getAccessToken());
    }

    public String getIconUrl() {
        return VK_ICON_URL;
    }
}
