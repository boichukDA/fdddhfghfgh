package ru.diaproject.vkplus.news.model.xml.items;

public class ParentHandler {
    private static boolean isContentSet = false;

    public ParentHandler(){
    }

    public boolean isContentSet() {
        return isContentSet;
    }

    public void setIsContentSet(boolean isContentSet) {
        this.isContentSet = isContentSet;
    }
}
