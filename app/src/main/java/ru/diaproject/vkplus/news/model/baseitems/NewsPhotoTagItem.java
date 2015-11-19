package ru.diaproject.vkplus.news.model.baseitems;

import ru.diaproject.vkplus.news.model.items.Photos;

public class NewsPhotoTagItem extends NewsEntityBase<Photos>{

    public NewsPhotoTagItem() {
        super(FilterType.PHOTO_TAG);
    }

}
