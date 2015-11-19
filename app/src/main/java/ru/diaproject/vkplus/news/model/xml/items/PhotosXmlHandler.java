package ru.diaproject.vkplus.news.model.xml.items;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;

public class PhotosXmlHandler extends ParentHandler {
    private volatile Photos currentPhotos;
    private List<PhotosInfo> photosInfos;
    private PhotosInfoXmlHandler photosInfoXmlHandler;

    public PhotosXmlHandler(){
        photosInfos = new ArrayList<>();
    }
    public void setXmlContentHandler(Element root) {
        Element wallPhotosElement = root.getChild("photos");
        photosInfoXmlHandler  = new PhotosInfoXmlHandler();
        photosInfoXmlHandler.setXmlContentHandler(wallPhotosElement);
        photosInfoXmlHandler.setResponse(photosInfos);

        wallPhotosElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                currentPhotos = new Photos();
            }
        });

        wallPhotosElement.getChild("count").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentPhotos.setCount(Integer.parseInt(body.trim()));
            }
        });

        wallPhotosElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                currentPhotos.setPhotos(photosInfos);
                reset();
            }
        });
    }

    public void reset(){
        photosInfos = new ArrayList<>();
        photosInfoXmlHandler.setResponse(photosInfos);
    }

    public Photos getResponse() {
        return currentPhotos;
    }
}
