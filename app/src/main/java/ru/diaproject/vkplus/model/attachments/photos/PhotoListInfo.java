package ru.diaproject.vkplus.model.attachments.photos;

import java.util.List;

import ru.diaproject.vkplus.model.DataObject;

public class PhotoListInfo extends DataObject {
    private List<String> photoList;

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }
}
