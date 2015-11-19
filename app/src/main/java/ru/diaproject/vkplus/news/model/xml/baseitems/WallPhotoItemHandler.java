package ru.diaproject.vkplus.news.model.xml.baseitems;

import android.sax.Element;

import ru.diaproject.vkplus.news.model.baseitems.NewsWallPhotoItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.xml.items.PhotosXmlHandler;

public class WallPhotoItemHandler {
    private NewsWallPhotoItem currentItem;
    public WallPhotoItemHandler(){

    }
    public void setXmlContentHandler(Element itemElement) {
    }

    public void setProcessEntity(NewsWallPhotoItem processEntity) {
        this.currentItem = processEntity;
       // currentItem.setAttachments(currentPhotos);
    }
}
