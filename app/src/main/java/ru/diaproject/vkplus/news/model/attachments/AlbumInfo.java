package ru.diaproject.vkplus.news.model.attachments;


import ru.diaproject.vkplus.news.model.items.PhotosInfo;

public class AlbumInfo {
    private Integer id;
    private PhotosInfo thumb;
    private Integer ownerId;
    private String title;
    private String description;
    private Integer created;
    private Integer updated;
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
