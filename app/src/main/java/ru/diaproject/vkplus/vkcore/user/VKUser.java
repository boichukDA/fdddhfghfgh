package ru.diaproject.vkplus.vkcore.user;

import android.os.Parcel;
import android.os.Parcelable;

import ru.diaproject.vkplus.vkcore.persistence.Persistence;

public class VKUser extends Persistence implements Parcelable {
    public static class VKUserBuilder{
        private String accessToken = "";
        private String accountId = "";
        private String secret;
        private boolean httpsRequired;

        public VKUserBuilder setAccessToken(String accessToken){
            this.accessToken = accessToken;
            return this;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getAccountId() {
            return accountId;
        }

        public VKUserBuilder setAccountId(String accountId) {
            this.accountId = accountId;
            return this;
        }
        public  VKUserBuilder setSecret(String secret) {
            this.secret = secret;
            return this;
        }

        public VKUser build(){
            return new VKUser(getAccessToken(), getAccountId(), getSecret(), isHttpsRequired());
        }

        public String getSecret() {
            return secret;
        }

        public VKUserBuilder setHttpsRequired(boolean httpsRequired) {
            this.httpsRequired = httpsRequired;
            return this;
        }

        public boolean isHttpsRequired() {
            return httpsRequired;
        }
    }
    public static final Parcelable.Creator<VKUser> CREATOR = new Parcelable.Creator<VKUser>() {
        public VKUser createFromParcel(Parcel in) {
            return new VKUser(in);
        }

        public VKUser[] newArray(int size) {
            return new VKUser[size];
        }
    };

    private String accountId;
    private VKUserConfiguration configuration;

    private VKUser(String accessToken, String accountId, String secret, boolean httpsRequired) {
        this.accountId = accountId;
        configuration = new VKUserConfiguration(accessToken, secret, httpsRequired);
    }

    private VKUser(Parcel in) {
        accountId = in.readString();
        configuration = in.readParcelable(VKUserConfiguration.class.getClassLoader());

    }

    public String getAccountId() {
        return accountId;
    }

    public VKUserConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String getPersistenceUniqueName() {
        return getClass().getCanonicalName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accountId);
        dest.writeParcelable(configuration, flags);
    }
}
