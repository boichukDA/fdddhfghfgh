package ru.diaproject.vkplus.model.attachments;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.items.Photos;
import ru.diaproject.vkplus.model.items.PhotosInfo;

public class Attachments implements Serializable {
    private List<Attachment> attachments;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @SuppressLint("LongLogTag")
         public Photos getPhotos() {
        Photos resultPhotos = new Photos();
        List<PhotosInfo> infoes = new ArrayList<>();
        // int additionalCount = 0;
        for (Attachment attachment:attachments) {
            if (attachment.getType().equals(AttachmentType.PHOTO))
                infoes.add((PhotosInfo) attachment.getItem());

            //if (attachment.getType().equals(AttachmentType.PHOTOS_LIST))
              /*  try {
                    additionalCount = ((List<String>) attachment.getItem()).size();
                }catch(Throwable e){
                    Log.e("Attachment getPhotos error", "exception",e);
                }*/
        }
        resultPhotos.setCount(infoes.size());//+additionalCount);
        resultPhotos.setPhotos(infoes);
        return resultPhotos;
    }

    @SuppressLint("LongLogTag")
    public List<VideoInfo> getVideos() {
        List<VideoInfo> infoes = new ArrayList<>();
        for (Attachment attachment:attachments) {
            if (attachment.getType().equals(AttachmentType.VIDEO))
                infoes.add((VideoInfo) attachment.getItem());
        }
        return infoes;
    }

    public List<AudioInfo> getAudios() {
        List<AudioInfo> infoes = new ArrayList<>();
        for (Attachment attachment:attachments) {
            if (attachment.getType().equals(AttachmentType.AUDIO))
                infoes.add((AudioInfo) attachment.getItem());
        }
        return infoes;
    }

    public boolean containsPhoto() {
        for (Attachment attachment:attachments) {
            if (attachment.getType().equals(AttachmentType.PHOTO))
                return true;
        }
       return false;
    }

    public boolean containsVideo() {
        for (Attachment attachment:attachments) {
            if (attachment.getType().equals(AttachmentType.VIDEO))
                return true;
        }
        return false;
    }

    public boolean containsAudio() {
        for (Attachment attachment:attachments) {
            if (attachment.getType().equals(AttachmentType.AUDIO))
                return true;
        }
        return false;
    }
}
