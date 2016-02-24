package ru.diaproject.vkplus.vkcore.queries;

public enum VKQuerySubMethod {
    GET(".get"), RECOMENDED(".getRecommended"), GET_BY_ID(".getById");

    VKQuerySubMethod(String value){
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }
}
