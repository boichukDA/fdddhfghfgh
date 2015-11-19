package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.LinkInfo;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;
import ru.diaproject.vkplus.news.model.json.items.PhotosInfoJsonHandler;


public class LinkInfoJsonHandler {

    public LinkInfo parse(JSONObject jsonObject) throws JSONException {
        LinkInfo info = new LinkInfo();
       info.setTitle(jsonObject.optString("title", ""));
        info.setDescription(jsonObject.optString("description", ""));
        info.setPreviewPage(jsonObject.optInt("preview_page", 0));
        info.setUrl(jsonObject.optString("url", ""));

        JSONObject jsonPhoto = jsonObject.optJSONObject("photo");
        if (jsonPhoto!= null){
            PhotosInfoJsonHandler handler = new PhotosInfoJsonHandler();
            PhotosInfo photoInfo = handler.parse(jsonPhoto);
            info.setPhoto(photoInfo);
        }
        return info;
    }
}
