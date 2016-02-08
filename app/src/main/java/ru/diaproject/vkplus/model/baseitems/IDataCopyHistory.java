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
import ru.diaproject.vkplus.model.items.NotesInfo;
import ru.diaproject.vkplus.model.items.Photos;

public interface IDataCopyHistory {
    Integer getFromId();

    Integer getDate();

    FilterType getPostType();

    String getText();

    Photos getAttachmentPhotos();

    List<PostedPhotoInfo> getAttachmentPostedPhotos();

    List<AudioInfo> getAttachmentAudios();

    List<VideoInfo> getAttachmentVideos();

    List<DocInfo> getAttachmentDocs();

    List<GraffitiInfo> getAttachmentGraffiti();

    List<LinkInfo> getAttachmentLinks();

    List<NotesInfo> getAttachmentNotes();

    List<AppInfo> getAttachmentApps();

    List<PollInfo> getAttachmentPolls();

    List<PageInfo> getAttachmentPages();

    List<AlbumInfo> getAttachmentAlbums();

    List<String> getPhotoList();
}
