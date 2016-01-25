package ru.diaproject.vkplus.news.model.baseitems;


import android.text.Spannable;

import java.util.HashMap;
import java.util.List;

import ru.diaproject.vkplus.VKPlusApplication;
import ru.diaproject.vkplus.core.utils.VkStringUtils;
import ru.diaproject.vkplus.news.model.IDataResult;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.groups.IDataGroup;
import ru.diaproject.vkplus.news.model.items.CommentsInfo;
import ru.diaproject.vkplus.news.model.items.CopyHistory;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;
import ru.diaproject.vkplus.news.model.items.LikesInfo;
import ru.diaproject.vkplus.news.model.items.PostSourceInfo;
import ru.diaproject.vkplus.news.model.items.RepostsInfo;
import ru.diaproject.vkplus.news.model.users.IDataUser;

public class DataPostItem extends DataMainItem implements IDataPostItem, IDataResult{
    private PostType postType;
    private Integer copyOwnerId;
    private Integer copyPostId;
    private Integer copyPostDate;
    private String text;

    private CommentsInfo comments;
    private LikesInfo likes;
    private RepostsInfo reposts;
    private PostSourceInfo postSourceInfo;

    private CopyHistory copyHistory;
    private Spannable spannableText;

    public CopyHistory getCopyHistory() {
        return copyHistory;
    }

    @Override
    public final FilterType getType() {
        return FilterType.POST;
    }

    public PostType getPostType() {
        return postType;
    }

    public Integer getCopyOwnerId() {
        return copyOwnerId;
    }

    public Integer getCopyPostId() {
        return copyPostId;
    }

    public Integer getCopyPostDate() {
        return copyPostDate;
    }

    public String getText() {
        return text;
    }

    public CommentsInfo getComments() {
        return comments;
    }

    public LikesInfo getLikes() {
        return likes;
    }

    public RepostsInfo getReposts() {
        return reposts;
    }

    public PostSourceInfo getPostSourceInfo() {
        return postSourceInfo;
    }

    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    public void setCopyOwnerId(Integer copyOwnerId) {
        this.copyOwnerId = copyOwnerId;
    }

    public void setCopyPostId(Integer copyPostId) {
        this.copyPostId = copyPostId;
    }

    public void setCopyPostDate(Integer copyPostDate) {
        this.copyPostDate = copyPostDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setComments(CommentsInfo comments) {
        this.comments = comments;
    }

    public void setLikes(LikesInfo likes) {
        this.likes = likes;
    }

    public void setReposts(RepostsInfo reposts) {
        this.reposts = reposts;
    }

    public void setPostSourceInfo(PostSourceInfo postSourceInfo) {
        this.postSourceInfo = postSourceInfo;
    }

    public void setCopyHistory(CopyHistory copyHistory) {
        this.copyHistory = copyHistory;
    }

    @Override
    public void prepareItems() {
        if (copyHistory != null){
            copyHistory.setContainsVideo(false);
            copyHistory.setContainsAudio(false);
            copyHistory.setContainsPhoto(false);

            List<CopyHistoryInfo> infoes = copyHistory.getItems();

            for (CopyHistoryInfo info: infoes) {
                if (info.getAttachmentVideos() != null) {
                    copyHistory.setContainsVideo(true);
                    break;
                }
            }

            for (CopyHistoryInfo info: infoes) {
                if (info.getAttachmentAudios() != null) {
                    copyHistory.setContainsAudio(true);
                    break;
                }
            }

            for (CopyHistoryInfo info: infoes) {
                if (info.getAttachmentPhotos() != null) {
                    copyHistory.setContainsPhoto(true);
                    break;
                }
            }
        }
    }

    @Override
    public void findOwner(HashMap<Integer, IDataUser> profiles, HashMap<Integer, IDataGroup> groups) {
        super.findOwner(profiles, groups);

        if (copyHistory!= null)
            for (CopyHistoryInfo info: copyHistory.getItems()) {
                info.findOwner(profiles, groups);
                info.prepareItems();
            }

        if (text!= null && !text.isEmpty())
            setSpannableText(VkStringUtils.prepareTextToVkFormat(text, VKPlusApplication.getStaticContext()));
    }

    public Spannable getSpannableText() {
        return spannableText;
    }

    public void setSpannableText(Spannable spannableText) {
        this.spannableText = spannableText;
    }
}
