package ru.diaproject.vkplus.news.model.users;

import org.xml.sax.ContentHandler;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.model.json.ResponseJsonHandler;
import ru.diaproject.vkplus.news.model.json.users.UserJsonHandler;

public class User implements VKDataCore {
    private Integer id;
    private String firstName;
    private String lastName;
    private DeactivatedType type;
    private boolean hidden;
    private Byte sex;
    private String screenName;
    private String photo50;
    private String photo100;
    private boolean online;
    private boolean onlineMobile;
    private String status;
    private UserResponseJsonHandler jsonHandler;

    public User(){
        jsonHandler = new UserResponseJsonHandler(this);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DeactivatedType getType() {
        return type;
    }

    public void setType(DeactivatedType type) {
        this.type = type;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnlineMobile() {
        return onlineMobile;
    }

    public void setOnlineMobile(boolean onlineMobile) {
        this.onlineMobile = onlineMobile;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public ContentHandler getXmlHandler() {
        return null;
    }

    @Override
    public JsonHandler getJsonHandler() {
        return jsonHandler;
    }
}
