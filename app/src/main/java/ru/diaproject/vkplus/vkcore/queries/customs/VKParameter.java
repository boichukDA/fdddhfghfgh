package ru.diaproject.vkplus.vkcore.queries.customs;

public enum VKParameter {
    FILTERS("filters"),FIELDS("fields"), MAX_PHOTOS("max_photos"),
    START_FROM("start_from"), USER_IDS("user_ids"), FEED("feed"),
    EXTENDED("extended"), OWNER_ID("owner_id"), PHOTOS("photos"), FEED_TYPE("feed_type");

    private String value;

    VKParameter(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
