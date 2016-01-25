package ru.diaproject.vkplus.news.model.attachments;

import java.util.List;

import ru.diaproject.vkplus.news.model.DataObject;

public class PhotoListInfo extends DataObject {
    private List<String> photoList;

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}