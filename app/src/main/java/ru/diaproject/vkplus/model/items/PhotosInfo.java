package ru.diaproject.vkplus.model.items;

import ru.diaproject.vkplus.model.baseitems.DataItem;

public class PhotosInfo extends DataItem {
    private Integer albumId;
    private String photo75;
    private String photo130;
    private String photo604;
    private String photo807;
    private String photo1280;
    private String photo2560;
    private Integer height;
    private Integer width;
    private String text;
    private String accessToken;
    //Extended
    private CommentsInfo comments;
    private LikesInfo likes;
    private boolean canComment;
    private boolean canRepost;


    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getPhoto75() {
        return photo75;
    }

    public void setPhoto75(String photo75) {
        this.photo75 = photo75;
    }

    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    public String getPhoto604() {
        return photo604;
    }

    public void setPhoto604(String photo604) {
        this.photo604 = photo604;
    }

    public String getPhoto807() {
        return photo807;
    }

    public void setPhoto807(String photo807) {
        this.photo807 = photo807;
    }

    public String getPhoto1280() {
        return photo1280;
    }

    public void setPhoto1280(String photo1280) {
        this.photo1280 = photo1280;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getPhoto2560() {
        return photo2560;
    }

    public void setPhoto2560(String photo2560) {
        this.photo2560 = photo2560;
    }

    public LikesInfo getLikes() {
        return likes;
    }

    public void setLikes(LikesInfo likes) {
        this.likes = likes;
    }

    public CommentsInfo getComments() {
        return comments;
    }

    public void setComments(CommentsInfo comments) {
        this.comments = comments;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public boolean isCanRepost() {
        return canRepost;
    }

    public void setCanRepost(boolean canRepost) {
        this.canRepost = canRepost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
