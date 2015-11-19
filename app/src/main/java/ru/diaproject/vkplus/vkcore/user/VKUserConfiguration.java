package ru.diaproject.vkplus.vkcore.user;

import android.os.Parcel;
import android.os.Parcelable;

public class VKUserConfiguration implements Parcelable {
    public static final Parcelable.Creator<VKUserConfiguration> CREATOR = new Parcelable.Creator<VKUserConfiguration>() {
        public VKUserConfiguration createFromParcel(Parcel in) {
            return new VKUserConfiguration(in);
        }

        public VKUserConfiguration[] newArray(int size) {
            return new VKUserConfiguration[size];
        }
    };

    private boolean allowNoHttps;
    private String accessToken = "";
    private String secret;
    private VKNewsUserConfiguration newsUserConfiguration;

    public VKUserConfiguration(String accessToken, String secret, boolean httpsRequired) {
        this.accessToken = accessToken;
        this.secret = secret;
        allowNoHttps = !httpsRequired;
        newsUserConfiguration = new VKNewsUserConfiguration((byte) 255);
    }

    public VKUserConfiguration(Parcel in) {
        allowNoHttps = in.readByte() != 0;
        accessToken = in.readString();
        secret = in.readString();
        newsUserConfiguration = new VKNewsUserConfiguration(in.readByte());
    }

    public boolean isAllowNoHttps() {
        return allowNoHttps;
    }

    public void setAllowNoHttps(boolean allowNoHttps) {
        this.allowNoHttps = allowNoHttps;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSecret(){
        return secret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (allowNoHttps ? 1 : 0));
        dest.writeString(accessToken);
        dest.writeString(secret);
        dest.writeByte(newsUserConfiguration.getNewsValues());
    }

    public VKNewsUserConfiguration getNewsUserConfiguration() {
        return newsUserConfiguration;
    }
}
