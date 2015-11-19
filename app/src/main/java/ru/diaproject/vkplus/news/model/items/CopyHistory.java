package ru.diaproject.vkplus.news.model.items;

import java.util.List;

public class CopyHistory {
    private List<CopyHistoryInfo> items;

    public List<CopyHistoryInfo> getItems() {
        return items;
    }

    public void setItems(List<CopyHistoryInfo> items) {
        this.items = items;
    }

    public boolean containsPhoto() {
        for (CopyHistoryInfo info: items)
        if(info.containsPhoto())
            return true;

        return false;
    }

    public boolean containsVideo() {
        for (CopyHistoryInfo info: items)
            if(info.containsVideo())
                return true;

        return false;
    }

    public boolean containsAudio() {
        for (CopyHistoryInfo info: items)
            if(info.containsAudio())
                return true;

        return false;
    }
}
