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
