package ru.diaproject.vkplus.news.model.xml.baseitems;

import android.sax.Element;

import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoTagItem;
import ru.diaproject.vkplus.news.model.xml.items.PhotoTagsXmlHandler;

public class PhotoTagItemHandler {
    private NewsPhotoTagItem currentItem;
    private PhotoTagsXmlHandler photosXmlHandler;
    public PhotoTagItemHandler(){

    }
    public void setXmlContentHandler(Element itemElement) {
        photosXmlHandler = new PhotoTagsXmlHandler();
        photosXmlHandler.setXmlContentHandler(itemElement);

    }

    public void setProcessEntity(NewsPhotoTagItem processEntity) {
        this.currentItem = processEntity;
        photosXmlHandler.setResponse(currentItem);
    }
}
