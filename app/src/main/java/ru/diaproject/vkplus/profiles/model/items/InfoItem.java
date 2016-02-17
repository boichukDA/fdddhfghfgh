package ru.diaproject.vkplus.profiles.model.items;

import ru.diaproject.vkplus.profiles.model.ProfileItemType;

public class InfoItem<T> {
    private ProfileItemType type;
    private T item;

    public InfoItem(ProfileItemType type){
        this.type = type;
    }
    public ProfileItemType getType() {
        return type;
    }

    public final void setItem(T item){
        this.item = item;
    }

     public T getItem(){
         return item;
     }
}
