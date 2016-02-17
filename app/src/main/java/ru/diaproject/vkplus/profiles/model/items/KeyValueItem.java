package ru.diaproject.vkplus.profiles.model.items;


import android.text.Spannable;

public class KeyValueItem {

    private String key;
    private String value;
    private Spannable spanKey;
    private Spannable spanValue;

    public KeyValueItem(){

    }

    public KeyValueItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Spannable getSpanKey() {
        return spanKey;
    }

    public Spannable getSpanValue() {
        return spanValue;
    }

    public void setSpanKey(Spannable spanKey) {
        this.spanKey = spanKey;
    }

    public void setSpanValue(Spannable spanValue) {
        this.spanValue = spanValue;
    }
}
