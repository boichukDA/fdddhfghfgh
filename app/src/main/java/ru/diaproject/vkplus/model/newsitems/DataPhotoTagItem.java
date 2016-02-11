package ru.diaproject.vkplus.model.newsitems;

public class DataPhotoTagItem extends DataMainItem{
    @Override
    public final FilterType getType() {
        return FilterType.PHOTO_TAG;
    }
}
