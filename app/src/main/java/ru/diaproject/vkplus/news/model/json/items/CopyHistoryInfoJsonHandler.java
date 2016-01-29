package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.attachments.Attachment;
import ru.diaproject.vkplus.news.model.attachments.AttachmentType;
import ru.diaproject.vkplus.news.model.attachments.Attachments;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.news.model.attachments.doc.DocType;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;
import ru.diaproject.vkplus.news.model.json.attachments.AttachmentsJsonHandler;

public class CopyHistoryInfoJsonHandler {
    public CopyHistoryInfo parse(JSONObject jsonObject) throws JSONException {
        CopyHistoryInfo info = new CopyHistoryInfo();
        Integer id = jsonObject.optInt("id", 0);
        info.setId(id);

        Integer ownerId = jsonObject.optInt("owner_id", 0);
        info.setOwnerId(ownerId);

        Integer fromId = jsonObject.optInt("from_id", 0);
        info.setFromId(fromId);

        Integer date= jsonObject.optInt("date", 0);
        info.setDate(date);

        try {
            FilterType type = FilterType.valueOf(jsonObject.optString("post_type", "").toUpperCase());
            info.setPostType(type);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String text = jsonObject.optString("text", "");
        info.setText(text);

        JSONArray attachments = jsonObject.optJSONArray("attachments");
        if (attachments!=null) {
            AttachmentsJsonHandler attHandler = new AttachmentsJsonHandler();
            Attachments attachms = attHandler.parse(attachments);
            setAttachments(info, attachms);
        }
        return info;
    }

    public  void setAttachments(CopyHistoryInfo item, Attachments attachments){
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
