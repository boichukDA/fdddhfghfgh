package ru.diaproject.vkplus.news.model.baseitems;

import ru.diaproject.vkplus.news.model.items.Photos;

public class NewsWallPhotoItem extends NewsEntityBase<Photos>{
    public NewsWallPhotoItem() {
        super(FilterType.WALL_PHOTO);
    }
}
