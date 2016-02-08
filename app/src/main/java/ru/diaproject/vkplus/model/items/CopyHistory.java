package ru.diaproject.vkplus.model.items;

import java.util.List;

import ru.diaproject.vkplus.model.DataObject;

public class CopyHistory extends DataObject {
    private List<CopyHistoryInfo> items;

    private boolean containsAudio;
    private boolean containsVideo;
    private boolean containsPhoto;

    public List<CopyHistoryInfo> getItems() {
        return items;
    }

    public void setItems(List<CopyHistoryInfo> items) {
        this.items = items;
    }

    public boolean isContainsAudio() {
        return containsAudio;
    }

    public void setContainsAudio(boolean containsAudio) {
        this.containsAudio = containsAudio;
    }

    public boolean isContainsVideo() {
        return containsVideo;
    }

    public void setContainsVideo(boolean containsVideo) {
        this.containsVideo = containsVideo;
    }

    public boolean isContainsPhoto() {
        return containsPhoto;
    }

    public void setContainsPhoto(boolean containsPhoto) {
        this.containsPhoto = containsPhoto;
    }
}
