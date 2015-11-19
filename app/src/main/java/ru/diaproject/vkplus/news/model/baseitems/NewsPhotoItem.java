package ru.diaproject.vkplus.news.model.baseitems;

import ru.diaproject.vkplus.news.model.items.Photos;

public class NewsPhotoItem extends NewsEntityBase<Photos>{

    public NewsPhotoItem() {
        super(FilterType.PHOTO);
    }

}
