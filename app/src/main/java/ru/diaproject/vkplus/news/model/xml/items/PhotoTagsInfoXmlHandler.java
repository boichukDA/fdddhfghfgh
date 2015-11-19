package ru.diaproject.vkplus.news.model.xml.items;


import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;

import java.util.List;

import ru.diaproject.vkplus.news.model.items.PhotosInfo;


public class PhotoTagsInfoXmlHandler {
    private PhotosInfo currentPhotoInfo;
    private List<PhotosInfo> response;

    public void setXmlContentHandler(Element root) {
        Element photosInfoElement = root.getChild("photo_tag");
        photosInfoElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                currentPhotoInfo = new PhotosInfo();
            }
        });

        photosInfoElement.getChild("id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentPhotoInfo.setId(Integer.parseInt(body.trim()));
            }
        });

        photosInfoElement.getChild("album_id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentPhotoInfo.setAlbumId(Integer.parseInt(body.trim()));
            }
        });

        photosInfoElement.getChild("owner_id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentPhotoInfo.setOwnerId(Integer.parseInt(body.trim()));
            }
        });

       /* photosInfoElement.getChild("src").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentPhotoInfo.setScr(body.trim());
            }
        });

        photosInfoElement.getChild("src_big").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentPhotoInfo.setScrBig(body.trim());
            }
        });*/

        photosInfoElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                response.add(currentPhotoInfo);
                currentPhotoInfo = null;
            }
        });
    }

    public void setResponse(List<PhotosInfo> response) {
        this.response = response;
    }
}
