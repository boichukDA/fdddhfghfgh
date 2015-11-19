package ru.diaproject.vkplus.vkcore.user;

public class VKNewsUserConfiguration {
    private byte newsValues;

    public VKNewsUserConfiguration(byte newsValues) {
        this.newsValues = newsValues;
    }

    public boolean isSet(int pos) {
        return (newsValues & (1 << pos)) != 0;
    }

    public void set(int pos, boolean value) {
        byte mask = (byte) (1 << pos);
        newsValues = (byte) ((newsValues & ~mask) | (value ? mask : 0));
    }

    public byte getNewsValues() {
        return newsValues;
    }
}
