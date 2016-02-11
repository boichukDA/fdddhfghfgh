package ru.diaproject.vkplus.model.attachments.photos;

import java.util.List;

import ru.diaproject.vkplus.model.DataObject;
import ru.diaproject.vkplus.model.JsonResponseParser;
import ru.diaproject.vkplus.json.items.PhotosResponseJsonHandler;

@JsonResponseParser(jsonParser = PhotosResponseJsonHandler.class)
public class Photos extends DataObject{
    private Integer count;
    private List<PhotosInfo> photos;

    public Photos() {
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

}
