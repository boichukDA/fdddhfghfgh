package ru.diaproject.vkplus.news.model.items;


import android.text.Spannable;

import java.util.List;

import ru.diaproject.vkplus.VKPlusApplication;
import ru.diaproject.vkplus.core.utils.VkStringUtils;
import ru.diaproject.vkplus.news.model.IDataResult;
import ru.diaproject.vkplus.news.model.attachments.AlbumInfo;
import ru.diaproject.vkplus.news.model.attachments.AppInfo;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.news.model.attachments.GraffitiInfo;
import ru.diaproject.vkplus.news.model.attachments.LinkInfo;
import ru.diaproject.vkplus.news.model.attachments.PageInfo;
import ru.diaproject.vkplus.news.model.attachments.PollInfo;
import ru.diaproject.vkplus.news.model.attachments.PostedPhotoInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.baseitems.DataItem;
import ru.diaproject.vkplus.news.model.baseitems.IDataCopyHistory;
import ru.diaproject.vkplus.news.model.baseitems.PostType;

public class CopyHistoryInfo extends DataItem implements IDataCopyHistory, IDataResult{
    private Integer fromId;
    private Integer date;
    private PostType postType;
    private String text;
    private Spannable spannableText;

    private Photos photos;
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

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
    public final List<String> getPhotoList() {
        return photoList;
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
    public void prepareItems() {
        if (videos!=null && !videos.isEmpty())
            for (VideoInfo video:videos)
                video.prepareItems();

        if (audios!=null && !audios.isEmpty())
            for (AudioInfo audioInfo:audios)
                audioInfo.prepareItems();

        if (text!= null && !text.isEmpty())
            setSpannableText(VkStringUtils.prepareTextToVkFormat(text, VKPlusApplication.getStaticContext()));
    }

    public Spannable getSpannableText() {
        return spannableText;
    }

    public void setSpannableText(Spannable spannableText) {
        this.spannableText = spannableText;
    }
}
