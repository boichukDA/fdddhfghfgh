package ru.diaproject.vkplus.model.newsitems;

public class DataWallPhotoItem extends DataMainItem{
    @Override
    public final FilterType getType() {
        return FilterType.WALL_PHOTO;
    }
}
