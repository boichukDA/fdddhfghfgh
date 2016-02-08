package ru.diaproject.vkplus.model.attachments.doc;

import ru.diaproject.vkplus.model.baseitems.DataItem;

public abstract  class DocInfo extends DataItem{
    private Integer date;
    private Integer size;
    private String title;
    private String ext;
    private String url;
    private DocType type;

    public DocInfo(DocType info){
        setType(info);
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public DocType getType() {
        return type;
    }

    public void setType(DocType type) {
        this.type = type;
    }
}
