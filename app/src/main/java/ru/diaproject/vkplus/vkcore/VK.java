package ru.diaproject.vkplus.vkcore;

import ru.diaproject.vkplus.database.model.User;

public enum VK {
    SINGLETON;

    private final Object[] secrets = new Object[]{ 5007122, "FhrsAU2IimyEd1lve90Q"};

    private final String VK_REDIRECT_URL  = "http://oauth.vk.com/blank.html";
    private final String AUTHORIZATION_URL = "http://oauth.vk.com/oauth/authorize?v=5.35&client_id=%s&scope=wall,friends,photos,offline,nohttps&redirect_uri=http://oauth.vk.com/blank.html&display=mobile&response_type=token";
    private final String VK_ICON_URL = "http://vk.com/images/emoji/";
    private User user;

    VK(){
    }
    public String getRedirectURl(){
        return  VK_REDIRECT_URL;
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


    public String getIconUrl() {
        return VK_ICON_URL;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
