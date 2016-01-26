package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.AlbumInfo;
import ru.diaproject.vkplus.news.model.attachments.AppInfo;
import ru.diaproject.vkplus.news.model.attachments.AttachNoteInfo;
import ru.diaproject.vkplus.news.model.attachments.Attachment;
import ru.diaproject.vkplus.news.model.attachments.AttachmentType;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.news.model.attachments.GraffitiInfo;
import ru.diaproject.vkplus.news.model.attachments.LinkInfo;
import ru.diaproject.vkplus.news.model.attachments.PageInfo;
import ru.diaproject.vkplus.news.model.attachments.PhotoListInfo;
import ru.diaproject.vkplus.news.model.attachments.PollInfo;
import ru.diaproject.vkplus.news.model.attachments.PostedPhotoInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;
import ru.diaproject.vkplus.news.model.json.items.PhotosInfoJsonHandler;

public class AttachmentJsonHandler {

    public AttachmentJsonHandler(){

    }

    public Attachment parse(JSONObject attachmentsJsonObject) throws JSONException {
        Attachment attachment =  new Attachment();
        AttachmentType type = AttachmentType.valueOf(attachmentsJsonObject.getString("type").toUpperCase());
        switch(type){
            case PHOTO:
                PhotosInfoJsonHandler piHandler = new PhotosInfoJsonHandler();
                PhotosInfo photoInfo = piHandler.parse(attachmentsJsonObject.getJSONObject("photo"));
                attachment.setItem(photoInfo);
                break;
            case POSTED_PHOTO:
                PostedPhotoInfoJsonHandler ppHandler = new PostedPhotoInfoJsonHandler();
                PostedPhotoInfo postedInfo = ppHandler.parse(attachmentsJsonObject.getJSONObject("posted_photo"));
                attachment.setItem(postedInfo);
                break;
            case VIDEO:
                VideoInfoJsonHandler videoInfoJsonHandler = new VideoInfoJsonHandler();
                VideoInfo videoInfo = videoInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("video"));
                attachment.setItem(videoInfo);
                break;
            case AUDIO:
                AudioInfoJsonHandler audioInfoJsonHandler = new AudioInfoJsonHandler();
                AudioInfo audioInfo = audioInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("audio"));
                attachment.setItem(audioInfo);
                break;
            case DOC:
                DocInfoJsonHandler docInfoJsonHandler = new DocInfoJsonHandler();
                DocInfo docInfo = docInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("doc"));
                attachment.setItem(docInfo);
                break;
            case GRAFFITI:
                GraffitiInfoJsonHandler graffitiInfoJsonHandler = new GraffitiInfoJsonHandler();
                GraffitiInfo graffitiInfo = graffitiInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("graffiti"));
                attachment.setItem(graffitiInfo);
                break;
            case LINK:
                LinkInfoJsonHandler linkInfoJsonHandler = new LinkInfoJsonHandler();
                LinkInfo linkInfo = linkInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("link"));
                attachment.setItem(linkInfo);
                break;
            case NOTE:
                AttachNodeInfoJsonHandler attachNodeInfoJsonHandler = new AttachNodeInfoJsonHandler();
                AttachNoteInfo aniInfo = attachNodeInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("note"));
                attachment.setItem(aniInfo);
                break;
            case APP:
                AppInfoJsonHandler appInfoJsonHandler = new AppInfoJsonHandler();
                AppInfo appInfo = appInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("app"));
                attachment.setItem(appInfo);
                break;
            case POLL:
                PollInfoJsonHandler pollInfoJsonHandler = new PollInfoJsonHandler();
                PollInfo pollInfo = pollInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("poll"));
                attachment.setItem(pollInfo);
                break;
            case PAGE:
                PageInfoJsonHandler pageInfoJsonHandler = new PageInfoJsonHandler();
                PageInfo pageInfo = pageInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("page"));
                attachment.setItem(pageInfo);
                break;
            case ALBUM:
                AlbumInfoJsonHandler albumInfoJsonHandler = new AlbumInfoJsonHandler();
                AlbumInfo albumInfo = albumInfoJsonHandler.parse(attachmentsJsonObject.getJSONObject("album"));
                attachment.setItem(albumInfo);
                break;
            case PHOTOS_LIST:
                PhotoListJsonHandler photoListJsonHandler = new PhotoListJsonHandler();
                PhotoListInfo list = photoListJsonHandler.parse(attachmentsJsonObject.getJSONArray("photos_list"));
                attachment.setItem(list);
                break;
            default:
        throw new JSONException("����������� ��� attachmentType " + type + " �� json " + attachmentsJsonObject.toString());
    }

        attachment.setType(type);
        return attachment;
    }
}
