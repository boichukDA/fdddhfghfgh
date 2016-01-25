package ru.diaproject.vkplus.news.model.items;

import java.util.List;

import ru.diaproject.vkplus.news.model.DataObject;

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
