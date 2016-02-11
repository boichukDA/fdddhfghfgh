package ru.diaproject.vkplus.model.newsitems;

public class DataPhotoItem extends DataMainItem{
    @Override
    public final FilterType getType() {
        return FilterType.PHOTO;
    }
}
