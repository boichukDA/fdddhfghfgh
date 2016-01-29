package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.news.model.attachments.doc.DocType;
import ru.diaproject.vkplus.news.model.attachments.doc.GifDocInfo;
import ru.diaproject.vkplus.news.model.json.users.UserJsonHandler;
import ru.diaproject.vkplus.news.model.users.IDataUser;


public class DocInfoJsonHandler {

    public DocInfo parse(JSONObject jsonObject) throws JSONException {
        DocInfo info = new GifDocInfo();
        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("owner_id"));
        info.setSize(jsonObject.optInt("size", 0));

        info.setUrl(jsonObject.getString("url"));
        info.setExt(jsonObject.getString("ext"));
        info.setTitle(jsonObject.optString("title", ""));

        int type = jsonObject.getInt("type");
        DocType docType = DocType.valueOf(type);
        info.setType(docType);

        if (docType.equals(DocType.GIF_DOC_TYPE)){
            GifDocInfo gifDocInfo = (GifDocInfo) info;
            JSONObject preview = jsonObject.getJSONObject("preview");
            JSONObject jsonPhoto = preview.getJSONObject("photo");
            JSONArray sizes = jsonPhoto.getJSONArray("sizes");

            GifDocInfo.Preview.Photo photo = new GifDocInfo.Preview.Photo();
            for (int index = 0; index < sizes.length();index++){
                GifDocInfo.Preview.Photo.Size currentSize = new GifDocInfo.Preview.Photo.Size();
                currentSize.setHeight(sizes.getJSONObject(index).getInt("height"));
                currentSize.setWidth(sizes.getJSONObject(index).getInt("width"));
                currentSize.setSrc(sizes.getJSONObject(index).getString("src"));

                String strType = sizes.getJSONObject(index).getString("type").toUpperCase();
                try {
                    GifDocInfo.Preview.Photo.Size.SizeType sizeType = GifDocInfo.Preview.Photo.Size.SizeType.valueOf(strType);
                    currentSize.setType(sizeType);
                    if (sizeType.equals(GifDocInfo.Preview.Photo.Size.SizeType.M))
                        photo.setSizeM(currentSize);
                    else photo.setSizeS(currentSize);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }

            GifDocInfo.Preview objPreview = new GifDocInfo.Preview();
            objPreview.setPhoto(photo);
            gifDocInfo.setPreview(objPreview);
        }
        return info;
    }
}
