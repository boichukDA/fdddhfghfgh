package ru.diaproject.vkplus.model.attachments;



import java.util.HashMap;

import ru.diaproject.vkplus.model.baseitems.DataItem;
import ru.diaproject.vkplus.model.groups.IDataGroup;
import ru.diaproject.vkplus.model.items.PhotosInfo;
import ru.diaproject.vkplus.model.users.IDataUser;

public class AlbumInfo extends DataItem {
    private PhotosInfo thumb;
    private Integer ownerId;
    private String title;
    private String description;
    private Integer created;
    private Integer updated;
    private Integer size;

    public PhotosInfo getThumb() {
        return thumb;
    }

    public void setThumb(PhotosInfo thumb) {
        this.thumb = thumb;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getUpdated() {
        return updated;
    }

    public void setUpdated(Integer updated) {
        this.updated = updated;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public void findOwner(HashMap<Integer, IDataUser> profiles, HashMap<Integer, IDataGroup> groups) {

    }
}
