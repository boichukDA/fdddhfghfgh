package ru.diaproject.vkplus.news.model.attachments;

public class GraffitiInfo {
    private Integer id;
    private Integer ownerId;
    private String photo200;
    private String photo586;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }

    public String getPhoto586() {
        return photo586;
    }

    public void setPhoto586(String photo586) {
        this.photo586 = photo586;
    }
}
