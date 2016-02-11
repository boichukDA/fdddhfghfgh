package ru.diaproject.vkplus.model.newsitems;

import java.util.List;

import ru.diaproject.vkplus.model.attachments.photos.AlbumInfo;
import ru.diaproject.vkplus.model.attachments.AppInfo;
import ru.diaproject.vkplus.model.attachments.audios.AudioInfo;
import ru.diaproject.vkplus.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.model.attachments.GraffitiInfo;
import ru.diaproject.vkplus.model.attachments.LinkInfo;
import ru.diaproject.vkplus.model.attachments.PageInfo;
import ru.diaproject.vkplus.model.attachments.polls.PollInfo;
import ru.diaproject.vkplus.model.attachments.photos.PostedPhotoInfo;
import ru.diaproject.vkplus.model.attachments.VideoInfo;
import ru.diaproject.vkplus.model.attachments.Friends;
import ru.diaproject.vkplus.model.attachments.notes.NotesInfo;
import ru.diaproject.vkplus.model.attachments.photos.Photos;


public interface IDataMainItem extends IDataItem {

    FilterType getType() ;

    Integer getDate();

    Integer getFinalPost();

    Boolean getCanEdit();

    Boolean getCanDelete();

    Integer getPostId();

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

    Friends getAttachmentFriends();

    List<String> getPhotoList();

}
