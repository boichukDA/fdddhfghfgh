package ru.diaproject.vkplus.json.baseitems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.attachments.Attachment;
import ru.diaproject.vkplus.model.attachments.AttachmentType;
import ru.diaproject.vkplus.model.attachments.Attachments;
import ru.diaproject.vkplus.model.attachments.audios.AudioInfo;
import ru.diaproject.vkplus.model.attachments.VideoInfo;
import ru.diaproject.vkplus.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.model.attachments.doc.DocType;
import ru.diaproject.vkplus.model.newsitems.PostType;
import ru.diaproject.vkplus.model.newsitems.DataMainItem;
import ru.diaproject.vkplus.model.newsitems.DataPostItem;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;
import ru.diaproject.vkplus.model.newsitems.copyhistory.CopyHistory;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.model.attachments.photos.PhotosInfo;
import ru.diaproject.vkplus.model.attachments.PostSourceInfo;
import ru.diaproject.vkplus.json.attachments.AttachmentsJsonHandler;
import ru.diaproject.vkplus.json.items.CommentsInfoJsonHandler;
import ru.diaproject.vkplus.json.items.CopyHistoryJsonHandler;
import ru.diaproject.vkplus.json.items.LikesInfoJsonHandler;
import ru.diaproject.vkplus.json.items.PostSourceInfoHandler;
import ru.diaproject.vkplus.json.items.RepostsInfoJsonHandler;

public class PostJsonHandler extends AttachmentJsonParser {
    @Override
    public IDataMainItem parse(JSONObject object, DataMainItem value) throws JSONException {

        DataPostItem postValue = (DataPostItem) value;
        PostType type = PostType.valueOf(object.optString("post_type", "").toUpperCase());
        postValue.setPostType(type);

        Integer copyOwnerId = object.optInt("copy_owner_id", 0);
        postValue.setCopyOwnerId(copyOwnerId);

        Integer copyPostId = object.optInt("copy_post_id", 0);
        postValue.setCopyPostId(copyPostId);

        Integer copyPostDate = object.optInt("copy_post_date", 0);
        postValue.setCopyPostDate(copyPostDate);

        String text = object.optString("text", "");
        postValue.setText(text);

        JSONArray attachments = object.optJSONArray("attachments");
        JSONArray copyHistory = object.optJSONArray("copy_history");

        if (copyHistory!=null){
            CopyHistoryJsonHandler copyHistoryJsonHandler = new CopyHistoryJsonHandler();
            CopyHistory history = copyHistoryJsonHandler.parse(copyHistory);
            postValue.setCopyHistory(history);
        }

        if (attachments!=null) {
            AttachmentsJsonHandler handler = new AttachmentsJsonHandler();
            Attachments attachms = handler.parse(attachments);
            setAttachments(value, attachms);
        }

        JSONObject postSource = object.optJSONObject("post_source");
        if (postSource != null){
            PostSourceInfoHandler postSourceInfoHandler = new PostSourceInfoHandler();
            PostSourceInfo postSourceInfo = postSourceInfoHandler.parse(postSource);
            postValue.setPostSourceInfo(postSourceInfo);
        }

        JSONObject comments = object.optJSONObject("comments");
        if (comments != null){
            CommentsInfoJsonHandler commentsInfoJsonHandler = new CommentsInfoJsonHandler();
            postValue.setComments(commentsInfoJsonHandler.parse(comments));
        }

        JSONObject likes = object.optJSONObject("likes");
        if (likes != null){
            LikesInfoJsonHandler likesInfoJsonHandler = new LikesInfoJsonHandler();
            postValue.setLikes(likesInfoJsonHandler.parse(likes));
        }

        JSONObject reposts = object.optJSONObject("reposts");
        if (reposts != null){
            RepostsInfoJsonHandler repostsInfoJsonHandler = new RepostsInfoJsonHandler();
            postValue.setReposts(repostsInfoJsonHandler.parse(reposts));
        }
        return value;
    }

    public  void setAttachments(DataMainItem item, Attachments attachments){
        Photos photos = new Photos();
        List<PhotosInfo> listPhotos = new ArrayList<>();
        List<AudioInfo> audios = new ArrayList<>();
        List<VideoInfo> videos = new ArrayList<>();
        List<DocInfo> docs = new ArrayList<>();

        for (Attachment attachment:attachments.getAttachments()){
            if (attachment.getType().equals(AttachmentType.AUDIO))
               audios.add((AudioInfo) attachment.getItem());

            if (attachment.getType().equals(AttachmentType.VIDEO))
                videos.add((VideoInfo) attachment.getItem());

            if (attachment.getType().equals(AttachmentType.PHOTO))
                listPhotos.add((PhotosInfo) attachment.getItem());

            if (attachment.getType().equals(AttachmentType.DOC)){
                DocInfo info = (DocInfo) attachment.getItem();
                if (info.getType().equals(DocType.GIF_DOC_TYPE))
                    docs.add(info);
            }
        }

        if (!listPhotos.isEmpty()){
            photos.setPhotos(listPhotos);
            photos.setCount(listPhotos.size());
            item.setPhotos(photos);
        }

        if(!audios.isEmpty())
            item.setAudios(audios);

        if (!videos.isEmpty())
            item.setVideos(videos);

        if (!docs.isEmpty())
            item.setDocs(docs);
    }
}
