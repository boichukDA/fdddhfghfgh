package ru.diaproject.vkplus.news.model.xml.baseitems;

import android.sax.Element;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.xml.items.PhotosXmlHandler;

public class PhotoItemHandler {
    private PhotosXmlHandler photosXmlHandler;
    public PhotoItemHandler(){

    }
    public void setXmlContentHandler(Element itemElement) {
        photosXmlHandler = new PhotosXmlHandler();
        photosXmlHandler.setXmlContentHandler(itemElement);
    }

    public void setProcessEntity(NewsEntityBase processEntity) {
        processEntity.setAttachments(photosXmlHandler.getResponse());
    }

    public void reset(){
        photosXmlHandler.reset();
    }
}
