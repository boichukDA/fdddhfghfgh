package ru.diaproject.vkplus.model.users.extusers.counters;


import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class Counters extends DataObject{
    public final static String JSON_ALBUMS = "albums";
    public final static String JSON_VIDEOS = "videos";
    public final static String JSON_AUDIOS = "audios";
    public final static String JSON_PHOTOS = "photos";
    public final static String JSON_NOTES = "notes";
    public final static String JSON_FRIENDS = "friends";

    public final static String JSON_GROUPS = "groups";
    public final static String JSON_ONLINE_FRIENDS = "online_friends";
    public final static String JSON_MUTUAL_FRIENDS = "mutual_friends";

    public final static String JSON_USER_VIDEOS = "user_videos";
    public final static String JSON_FOLLOWERS = "followers";
    public final static String JSON_PAGES = "pages";

    public static Counters parseObject(JSONObject object){
        Counters counters = new Counters();
        counters.setAlbums(object.optInt(JSON_ALBUMS));
        counters.setVideos(object.optInt(JSON_VIDEOS));
        counters.setAudios(object.optInt(JSON_AUDIOS));

        counters.setPhotos(object.optInt(JSON_PHOTOS));
        counters.setNotes(object.optInt(JSON_NOTES));
        counters.setFriends(object.optInt(JSON_FRIENDS));

        counters.setGroups(object.optInt(JSON_GROUPS));
        counters.setOnlineFriends(object.optInt(JSON_ONLINE_FRIENDS));
        counters.setMutualFriends(object.optInt(JSON_MUTUAL_FRIENDS));

        counters.setUserVideos(object.optInt(JSON_USER_VIDEOS));
        counters.setFollowers(object.optInt(JSON_FOLLOWERS));
        counters.setPages(object.optInt(JSON_PAGES));
        return counters;
    }

    private Integer albums;
    private Integer videos;
    private Integer audios;
    private Integer photos;
    private Integer notes;
    private Integer friends;
    private Integer groups;
    private Integer onlineFriends;
    private Integer mutualFriends;
    private Integer userVideos;
    private Integer followers;
    private Integer pages;

    public Integer getAlbums() {
        return albums;
    }

    public void setAlbums(Integer albums) {
        this.albums = albums;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getAudios() {
        return audios;
    }

    public void setAudios(Integer audios) {
        this.audios = audios;
    }

    public Integer getPhotos() {
        return photos;
    }

    public void setPhotos(Integer photos) {
        this.photos = photos;
    }

    public Integer getNotes() {
        return notes;
    }

    public void setNotes(Integer notes) {
        this.notes = notes;
    }

    public Integer getFriends() {
        return friends;
    }

    public void setFriends(Integer friends) {
        this.friends = friends;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public Integer getOnlineFriends() {
        return onlineFriends;
    }

    public void setOnlineFriends(Integer onlineFriends) {
        this.onlineFriends = onlineFriends;
    }

    public Integer getMutualFriends() {
        return mutualFriends;
    }

    public void setMutualFriends(Integer mutualFriends) {
        this.mutualFriends = mutualFriends;
    }

    public Integer getUserVideos() {
        return userVideos;
    }

    public void setUserVideos(Integer userVideos) {
        this.userVideos = userVideos;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
