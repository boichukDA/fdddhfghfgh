package ru.diaproject.vkplus.model.baseitems;

import java.util.List;

import ru.diaproject.vkplus.model.attachments.AlbumInfo;
import ru.diaproject.vkplus.model.attachments.AppInfo;
import ru.diaproject.vkplus.model.attachments.AudioInfo;
import ru.diaproject.vkplus.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.model.attachments.GraffitiInfo;
import ru.diaproject.vkplus.model.attachments.LinkInfo;
import ru.diaproject.vkplus.model.attachments.PageInfo;
import ru.diaproject.vkplus.model.attachments.PollInfo;
import ru.diaproject.vkplus.model.attachments.PostedPhotoInfo;
import ru.diaproject.vkplus.model.attachments.VideoInfo;
import ru.diaproject.vkplus.model.items.Friends;
import ru.diaproject.vkplus.model.items.NotesInfo;
import ru.diaproject.vkplus.model.items.Photos;

public abstract class DataMainItem extends DataItem implements IDataMainItem {
    private Integer date;
    private Integer finalPost;
    private Boolean canEdit;
    private Boolean canDelete;
    private Integer postId;

    private Photos photos;
    private Friends friends;
    private List<PostedPhotoInfo> postedPhotos;
    private List<AudioInfo> audios;
    private List<VideoInfo> videos;
    private List<DocInfo> docs;
    private List<GraffitiInfo> graffiti;
    private List<LinkInfo> links;
    private List<NotesInfo> notes;
    private List<AppInfo> apps;
    private List<PollInfo> polls;
    private List<PageInfo> pages;
    private List<AlbumInfo> albums;
    private List<String> photoList;

    public Integer getDate() {
        return date;
    }

    public Integer getFinalPost() {
        return finalPost;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public Integer getPostId() {
        return postId;
    }

    @Override
    public final Photos getAttachmentPhotos() {
        return photos;
    }

    @Override
    public final List<PostedPhotoInfo> getAttachmentPostedPhotos() {
        return postedPhotos;
    }

    @Override
    public final List<AudioInfo> getAttachmentAudios() {
        return audios;
    }

    @Override
    public final List<VideoInfo> getAttachmentVideos() {
        return videos;
    }

    @Override
    public final List<DocInfo> getAttachmentDocs() {
        return docs;
    }

    @Override
    public final List<GraffitiInfo> getAttachmentGraffiti() {
        return graffiti;
    }

    @Override
    public final List<LinkInfo> getAttachmentLinks() {
        return links;
    }

    @Override
    public final List<NotesInfo> getAttachmentNotes() {
        return notes;
    }

    @Override
    public final List<AppInfo> getAttachmentApps() {
        return apps;
    }

    @Override
    public final List<PollInfo> getAttachmentPolls() {
        return polls;
    }

    @Override
    public final List<PageInfo> getAttachmentPages() {
        return pages;
    }

    @Override
    public final List<AlbumInfo> getAttachmentAlbums() {
        return albums;
    }

    @Override
    public final Friends getAttachmentFriends() {
        return friends;
    }

    @Override
    public final List<String> getPhotoList() {
        return photoList;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void setFinalPost(Integer finalPost) {
        this.finalPost = finalPost;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public void setPostedPhotos(List<PostedPhotoInfo> postedPhotos) {
        this.postedPhotos = postedPhotos;
    }

    public void setAudios(List<AudioInfo> audios) {
        this.audios = audios;
    }

    public void setVideos(List<VideoInfo> videos) {
        this.videos = videos;
    }

    public void setDocs(List<DocInfo> docs) {
        this.docs = docs;
    }

    public void setGraffiti(List<GraffitiInfo> graffiti) {
        this.graffiti = graffiti;
    }

    public void setLinks(List<LinkInfo> links) {
        this.links = links;
    }

    public void setNotes(List<NotesInfo> notes) {
        this.notes = notes;
    }

    public void setApps(List<AppInfo> apps) {
        this.apps = apps;
    }

    public void setPolls(List<PollInfo> polls) {
        this.polls = polls;
    }

    public void setPages(List<PageInfo> pages) {
        this.pages = pages;
    }

    public void setAlbums(List<AlbumInfo> albums) {
        this.albums = albums;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    @Override
    public Integer getId() {
        return getOwnerId().hashCode()|getDate().hashCode();
    }

    public void setFriends(Friends friends) {
        this.friends = friends;
    }
}
