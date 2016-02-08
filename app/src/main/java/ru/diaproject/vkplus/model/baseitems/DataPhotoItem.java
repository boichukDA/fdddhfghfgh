package ru.diaproject.vkplus.model.baseitems;

public class DataPhotoItem extends DataMainItem{
    @Override
    public final FilterType getType() {
        return FilterType.PHOTO;
    }
}
