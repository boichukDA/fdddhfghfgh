package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.DataFriendItem;
import ru.diaproject.vkplus.news.model.baseitems.DataMainItem;
import ru.diaproject.vkplus.news.model.baseitems.DataNoteItem;
import ru.diaproject.vkplus.news.model.baseitems.DataPhotoItem;
import ru.diaproject.vkplus.news.model.baseitems.DataPhotoTagItem;
import ru.diaproject.vkplus.news.model.baseitems.DataPostItem;
import ru.diaproject.vkplus.news.model.baseitems.DataWallPhotoItem;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;

public class BaseItemJsonHandler {

    public IDataMainItem parse(JSONObject itemJsonObject) throws JSONException {
        FilterType type = FilterType.valueOf(itemJsonObject.getString("type").toUpperCase());
        DataMainItem baseItem;
        AttachmentJsonParser parser;
        switch (type){
                case POST:
                    parser = new PostJsonHandler();
                    baseItem = new DataPostItem();
                    break;

                case FRIEND:
                    parser = new FriendJsonHandler();
                    baseItem = new DataFriendItem();
                    break;

                case WALL_PHOTO:
                    parser = new WallPhotoJsonParser();
                    baseItem = new DataWallPhotoItem();
                    break;

                case PHOTO:
                    parser = new PhotoJsonHandler();
                    baseItem = new DataPhotoItem();
                    break;

                case PHOTO_TAG:
                    parser = new PhotoTagJsonHandler();
                    baseItem = new DataPhotoTagItem();
                    break;
            case NOTE:
                    parser = new NoteJsonHandler();
                    baseItem = new DataNoteItem();
                break;
                default:
                    throw new JSONException("a��� ������ ����");
        }
        Boolean canEdit = itemJsonObject.optInt("can_edit", 0)>0;
        baseItem.setCanEdit(canEdit);
        Integer sourceId = itemJsonObject.getInt("source_id");
        baseItem.setOwnerId(sourceId);
        Integer date = itemJsonObject.getInt("date");
        baseItem.setDate(date);

        Integer finalPost = itemJsonObject.optInt("final_post", 0);
        baseItem.setFinalPost(finalPost);

        Boolean canDelete = itemJsonObject.optInt("can_delete",0)>0;
        baseItem.setCanDelete(canDelete);

        Integer postId = itemJsonObject.optInt("source_id", 0);
        baseItem.setPostId(postId);

        IDataMainItem result = parser.parse(itemJsonObject, baseItem);

        return result;
    }
}
