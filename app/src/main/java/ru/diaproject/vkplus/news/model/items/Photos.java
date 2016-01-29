package ru.diaproject.vkplus.news.model.items;

import java.util.List;

import ru.diaproject.vkplus.news.model.DataObject;
import ru.diaproject.vkplus.news.model.JsonResponseParser;
import ru.diaproject.vkplus.news.model.json.items.PhotosResponseJsonHandler;
import ru.diaproject.vkplus.news.model.json.users.UserResponseJsonHandler;

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
