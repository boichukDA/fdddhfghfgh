package ru.diaproject.vkplus.vkcore.queries;

public enum VKQueryResponseTypes {
    XML(".xml"), JSON("");

    private String value;
    VKQueryResponseTypes(String value){
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }
}
