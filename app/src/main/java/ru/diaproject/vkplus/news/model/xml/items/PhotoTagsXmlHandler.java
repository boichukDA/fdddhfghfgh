package ru.diaproject.vkplus.news.model.xml.items;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoTagItem;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;

public class PhotoTagsXmlHandler {
    private Photos currentPhotos;
    private NewsPhotoTagItem response;
    private List<PhotosInfo> photosInfos;

    public PhotoTagsXmlHandler(){
        photosInfos = new ArrayList<>();
    }
    public void setXmlContentHandler(Element root) {
        Element photosElement = root.getChild("photo_tags");
        final PhotoTagsInfoXmlHandler photosInfoXmlHandler = new PhotoTagsInfoXmlHandler();
        photosInfoXmlHandler.setXmlContentHandler(photosElement);
        photosInfoXmlHandler.setResponse(photosInfos);

        photosElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                currentPhotos = new Photos();

            }
        });

        photosElement.getChild("count").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentPhotos.setCount(Integer.parseInt(body.trim()));
            }
        });

        photosElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                currentPhotos.setPhotos(photosInfos);
                response.setAttachments(currentPhotos);
            }
        });
    }

    public void setResponse(NewsPhotoTagItem response) {
        this.response = response;
    }
}
