package ru.diaproject.vkplus.database.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "USERS")
public class User implements Serializable{
    public static class UserBuilder{
        private String accessToken = "";
        private String accountId = "";
        private String secret;

        public UserBuilder setAccessToken(String accessToken){
            this.accessToken = accessToken;
            return this;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getAccountId() {
            return accountId;
        }

        public UserBuilder setAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }
        public  UserBuilder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public User build(){
            return new User(getAccessToken(), getAccountId(), getSecret(), true);
        }

        public String getSecret() {
            return secret;
        }

    }

    public final static String ID_COLUMN = "USER_ID";
    public final static String ACCESS_TOKEN_COLUMN = "ACCESS_TOKEN";
    public final static String ACCOUNT_ID_COLUMN = "ACCOUNT_ID";
    public final static String SECRET_COLUMN = "SECRET";
    public final static String IS_CURRENT_COLUMN = "IS_CURRENT";

    public User(){

    }

    public User(String accessToken, String accountId, String secret, boolean isCurrent) {
        this.accessToken = accessToken;
        this.accountId = accountId;
        this.secret = secret;
        this.isCurrent = isCurrent;
    }


    @DatabaseField( unique = true, generatedId = true, dataType = DataType.INTEGER, columnName = ID_COLUMN)
    private int userId;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = ACCESS_TOKEN_COLUMN)
    private String accessToken = "";

    @DatabaseField(canBeNull = false, unique = true, dataType = DataType.STRING, columnName = ACCOUNT_ID_COLUMN)
    private String accountId = "";

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = SECRET_COLUMN)
    private String secret;

    @DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = IS_CURRENT_COLUMN)
    private boolean isCurrent;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true)
    private MainConfiguration mainConfiguration;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoCreate = true)
    private NewsConfiguration newsConfiguration;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public MainConfiguration getMainConfiguration() {
        return mainConfiguration;
    }

    public void setMainConfiguration(MainConfiguration mainConfiguration) {
        this.mainConfiguration = mainConfiguration;
    }

    public NewsConfiguration getNewsConfiguration() {
        return newsConfiguration;
    }

    public void setNewsConfiguration(NewsConfiguration newsConfiguration) {
        this.newsConfiguration = newsConfiguration;
    }
}
