package ru.diaproject.vkplus.news.model.items;

import org.xml.sax.ContentHandler;

import java.util.List;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.model.json.items.PhotosResponseJsonHandler;

public class Photos implements VKDataCore {
    private Integer count;
    private List<PhotosInfo> photos;
    private PhotosResponseJsonHandler jsonHandler;

    public Photos(){
        jsonHandler = new PhotosResponseJsonHandler(this);
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PhotosInfo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosInfo> photos) {
        this.photos = photos;
    }

    @Override
    public ContentHandler getXmlHandler() {
        return null;
    }

    @Override
    public JsonHandler getJsonHandler() {
        return jsonHandler;
    }
}
