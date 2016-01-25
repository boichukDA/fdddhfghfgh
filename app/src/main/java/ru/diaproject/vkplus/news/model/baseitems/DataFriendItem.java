package ru.diaproject.vkplus.news.model.baseitems;

public class DataFriendItem extends DataMainItem{
    @Override
    public final FilterType getType() {
        return FilterType.FRIEND;
    }
}
