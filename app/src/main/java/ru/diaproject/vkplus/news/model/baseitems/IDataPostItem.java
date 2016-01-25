package ru.diaproject.vkplus.news.model.baseitems;


import android.text.Spannable;

import ru.diaproject.vkplus.news.model.items.CommentsInfo;
import ru.diaproject.vkplus.news.model.items.CopyHistory;
import ru.diaproject.vkplus.news.model.items.LikesInfo;
import ru.diaproject.vkplus.news.model.items.PostSourceInfo;
import ru.diaproject.vkplus.news.model.items.RepostsInfo;

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
