package ru.diaproject.vkplus.model.newsitems;


import android.text.Spannable;

import ru.diaproject.vkplus.model.items.CommentsInfo;
import ru.diaproject.vkplus.model.newsitems.copyhistory.CopyHistory;
import ru.diaproject.vkplus.model.items.LikesInfo;
import ru.diaproject.vkplus.model.attachments.PostSourceInfo;
import ru.diaproject.vkplus.model.items.RepostsInfo;

public interface IDataPostItem extends IDataMainItem{
    PostType getPostType();

    Integer getCopyOwnerId() ;

    Integer getCopyPostId();

    Integer getCopyPostDate();

    String getText();

    CommentsInfo getComments();

    LikesInfo getLikes();

    RepostsInfo getReposts();

    PostSourceInfo getPostSourceInfo();

    CopyHistory getCopyHistory();

    Spannable getSpannableText();
}
