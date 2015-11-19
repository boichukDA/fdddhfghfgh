package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsFriendItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsNoteItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoTagItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsWallPhotoItem;


public class BaseItemJsonHandler {

    public NewsEntityBase parse(JSONObject itemJsonObject) throws JSONException {
        FilterType type = FilterType.valueOf(itemJsonObject.getString("type").toUpperCase());
        NewsEntityBase baseItem;
        AttachmentJsonParser parser;
        switch (type){
                case POST:
                    parser = new PostJsonHandler();
                    baseItem = new NewsPostItem();
                    break;

                case FRIEND:
                    parser = new FriendJsonHandler();
                    baseItem = new NewsFriendItem();
                    break;

                case WALL_PHOTO:
                    parser = new WallPhotoJsonParser();
                    baseItem = new NewsWallPhotoItem();
                    break;

                case PHOTO:
                    parser = new PhotoJsonHandler();
                    baseItem = new NewsPhotoItem();
                    break;

                case PHOTO_TAG:
                    parser = new PhotoTagJsonHandler();
                    baseItem = new NewsPhotoTagItem();
                    break;
            case NOTE:
                    parser = new NoteJsonHandler();
                    baseItem = new NewsNoteItem();
                break;
                default:
                    throw new JSONException("a��� ������ ����");
        }
        Boolean canEdit = itemJsonObject.optInt("can_edit", 0)>0?true:false;
        baseItem.setCanEdit(canEdit);
        Integer sourceId = itemJsonObject.getInt("source_id");
        baseItem.setSourceId(sourceId);
        Integer date = itemJsonObject.getInt("date");
        baseItem.setDate(date);

        Integer finalPost = itemJsonObject.optInt("final_post", 0);
        baseItem.setFinalPost(finalPost);

        Boolean canDelete = itemJsonObject.optInt("can_delete",0)>0?true:false;
        baseItem.setCanDelete(canDelete);

        Integer postId = itemJsonObject.optInt("source_id", 0);
        baseItem.setPostId(postId);

        baseItem = parser.parse(itemJsonObject, baseItem);

        return baseItem;
    }
}
