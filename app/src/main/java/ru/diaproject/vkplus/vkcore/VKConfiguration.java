package ru.diaproject.vkplus.vkcore;

import ru.diaproject.vkplus.vkcore.persistence.Persistence;

public class VKConfiguration extends Persistence{

    private boolean isFirstTime = false;

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setIsFirstTime(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }
}
