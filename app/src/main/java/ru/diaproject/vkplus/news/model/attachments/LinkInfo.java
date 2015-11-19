package ru.diaproject.vkplus.news.model.attachments;

import ru.diaproject.vkplus.news.model.items.PhotosInfo;

public class LinkInfo {
    private String url;
    private String title;
    private String description;
    private PhotosInfo photo;
    private Integer previewPage;
    private String previewUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public PhotosInfo getPhoto() {
        return photo;
    }

    public void setPhoto(PhotosInfo photo) {
        this.photo = photo;
    }

    public Integer getPreviewPage() {
        return previewPage;
    }

    public void setPreviewPage(Integer previewPage) {
        this.previewPage = previewPage;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
