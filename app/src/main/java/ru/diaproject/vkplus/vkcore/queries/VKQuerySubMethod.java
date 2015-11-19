package ru.diaproject.vkplus.vkcore.queries;

public enum VKQuerySubMethod {
    DEFAULT(".get"), RECOMENDED(".getRecommended");

    VKQuerySubMethod(String value){
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }
}
