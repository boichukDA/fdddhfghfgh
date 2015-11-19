package ru.diaproject.vkplus.news.model.baseitems;

import ru.diaproject.vkplus.news.model.attachments.Attachments;
import ru.diaproject.vkplus.news.model.items.CommentsInfo;
import ru.diaproject.vkplus.news.model.items.LikesInfo;
import ru.diaproject.vkplus.news.model.items.RepostsInfo;
import ru.diaproject.vkplus.news.model.items.PostSourceInfo;

public class NewsPostItem extends NewsEntityBase<Attachments> {
    private PostType postType;
    private Integer copyOwnerId;
    private Integer copyPostId;
    private Integer copyPostDate;
    private String text;

    private CommentsInfo comments;
    private LikesInfo likes;
    private RepostsInfo reposts;
    private PostSourceInfo postSourceInfo;

    public NewsPostItem() {
        super(FilterType.POST);
    }

    public PostType getPostType() {
        return postType;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public Integer getCopyOwnerId() {
        return copyOwnerId;
    }

    public void setCopyOwnerId(Integer copyOwnerId) {
        this.copyOwnerId = copyOwnerId;
    }

    public Integer getCopyPostId() {
        return copyPostId;
    }

    public void setCopyPostId(Integer copyPostId) {
        this.copyPostId = copyPostId;
    }

    public Integer getCopyPostDate() {
        return copyPostDate;
    }

    public void setCopyPostDate(Integer copyPostDate) {
        this.copyPostDate = copyPostDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CommentsInfo getComments() {
        return comments;
    }

    public void setComments(CommentsInfo comments) {
        this.comments = comments;
    }

    public LikesInfo getLikes() {
        return likes;
    }

    public void setLikes(LikesInfo likes) {
        this.likes = likes;
    }

    public RepostsInfo getReposts() {
        return reposts;
    }

    public void setReposts(RepostsInfo reposts) {
        this.reposts = reposts;
    }

    public PostSourceInfo getPostSourceInfo() {
        return postSourceInfo;
    }

    public void setPostSourceInfo(PostSourceInfo postSourceInfo) {
        this.postSourceInfo = postSourceInfo;
    }

    public boolean containsPhoto() {
        if (getAttachments()!= null && getAttachments().containsPhoto())
            return true;

        if (getCopyHistory()!= null && getCopyHistory().containsPhoto())
            return true;

        return false;
    }

    public boolean containsVideo() {
        if (getAttachments()!= null && getAttachments().containsVideo())
            return true;

        if (getCopyHistory()!= null && getCopyHistory().containsVideo())
            return true;

        return false;
    }

    public boolean containsAudio() {
        if (getAttachments()!= null && getAttachments().containsAudio())
            return true;

        if (getCopyHistory()!= null && getCopyHistory().containsAudio())
            return true;

        return false;
    }
}
