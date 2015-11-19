package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.attachments.Attachments;
import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.baseitems.PostType;
import ru.diaproject.vkplus.news.model.items.CopyHistory;
import ru.diaproject.vkplus.news.model.items.PostSourceInfo;
import ru.diaproject.vkplus.news.model.json.attachments.AttachmentsJsonHandler;
import ru.diaproject.vkplus.news.model.json.items.CommentsInfoJsonHandler;
import ru.diaproject.vkplus.news.model.json.items.CopyHistoryJsonHandler;
import ru.diaproject.vkplus.news.model.json.items.LikesInfoJsonHandler;
import ru.diaproject.vkplus.news.model.json.items.PostSourceInfoHandler;
import ru.diaproject.vkplus.news.model.json.items.RepostsInfoJsonHandler;

public class PostJsonHandler extends AttachmentJsonParser {
    @Override
    public NewsPostItem parse(JSONObject object, NewsEntityBase value) throws JSONException {

        NewsPostItem postValue = (NewsPostItem) value;
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
            value.setAttachments(attachms);
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
        return (NewsPostItem) value;
    }
}
