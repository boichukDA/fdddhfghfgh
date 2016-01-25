package ru.diaproject.vkplus.news.model.baseitems;

public class DataWallPhotoItem extends DataMainItem{
    @Override
    public final FilterType getType() {
        return FilterType.WALL_PHOTO;
    }
}
