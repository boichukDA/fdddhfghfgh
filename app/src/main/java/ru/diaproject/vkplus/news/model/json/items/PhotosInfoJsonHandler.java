package ru.diaproject.vkplus.news.model.json.items;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.items.PhotosInfo;

public class PhotosInfoJsonHandler {
    public PhotosInfo parse(JSONObject photosJsonObject) throws JSONException {
        PhotosInfo info = new PhotosInfo();

        Integer id = photosJsonObject.optInt("id", 0);
        info.setId(id);

        Integer ownerId = photosJsonObject.optInt("owner_id", 0);
        info.setOwnerId(ownerId);

        Integer albumId = photosJsonObject.optInt("album_id", 0);
        info.setAlbumId(albumId);

        String photo75 = photosJsonObject.optString("photo_75", "");
        info.setPhoto75(photo75);

        String photo130 = photosJsonObject.optString("photo_130", "");
        info.setPhoto130(photo130);

        String photo604= photosJsonObject.optString("photo_604", "");
        info.setPhoto604(photo604);

        String photo807 = photosJsonObject.optString("photo_807", "");
        info.setPhoto807(photo807);

        String photo1280 = photosJsonObject.optString("photo_1280", "");
        info.setPhoto1280(photo1280);

        String photo2560 = photosJsonObject.optString("photo_2560", "");
        info.setPhoto2560(photo2560);

        Integer width = photosJsonObject.optInt("width", 0);
        info.setWidth(width);

        Integer height = photosJsonObject.optInt("height", 0);
        info.setHeight(height);

        String text = photosJsonObject.optString("text", "");
        info.setText(text);

        String accessToken = photosJsonObject.optString("access_key", "");
        info.setAccessToken(accessToken);

        JSONObject comments = photosJsonObject.optJSONObject("comments");
        if (comments != null){
            CommentsInfoJsonHandler commentsInfoJsonHandler = new CommentsInfoJsonHandler();
            info.setComments(commentsInfoJsonHandler.parse(comments));
        }

        JSONObject likes = photosJsonObject.optJSONObject("likes");
        if (likes != null){
            LikesInfoJsonHandler likesInfoJsonHandler = new LikesInfoJsonHandler();
            info.setLikes(likesInfoJsonHandler.parse(likes));
        }

        info.setCanComment(photosJsonObject.optInt("can_comment",0)>0);
        info.setCanRepost(photosJsonObject.optInt("can_repost",0)>0);

        return info;
    }
}
