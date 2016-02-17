package ru.diaproject.vkplus.model.users.extusers.cropphoto;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.json.items.PhotosInfoJsonHandler;
import ru.diaproject.vkplus.model.attachments.photos.PhotosInfo;

public class CropPhoto {
    public static final String JSON_PHOTO = "photo";
    public static final String JSON_RECT = "rect";

    public static CropPhoto parseObject(JSONObject object){
        CropPhoto cropPhoto = new CropPhoto();
        PhotosInfoJsonHandler handler = new PhotosInfoJsonHandler();
        try {
            cropPhoto.setPhoto(handler.parse(object.optJSONObject(JSON_PHOTO)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cropPhoto.setRect(Rect.parseObject(object.optJSONObject(JSON_RECT)));
        return cropPhoto;
    }
    private PhotosInfo photo;
    private Rect rect;

    public PhotosInfo getPhoto() {
        return photo;
    }

    public void setPhoto(PhotosInfo photo) {
        this.photo = photo;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}
