package ru.diaproject.vkplus.news.model.baseitems;

public class DataPhotoTagItem extends DataMainItem{
    @Override
    public final FilterType getType() {
        return FilterType.PHOTO_TAG;
    }
}
