package ru.diaproject.vkplus.vkcore.queries;

public enum VKQueryType {
    NEWS("newsfeed"), USERS("users"), PHOTO("photos");

    private CharSequence value;

    VKQueryType(String value){
        this.value = value;
    }
    public CharSequence getValue() {
        return value;
    }
}
