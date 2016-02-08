package ru.diaproject.vkplus.model.baseitems;


import android.text.Spannable;

import ru.diaproject.vkplus.model.items.CommentsInfo;
import ru.diaproject.vkplus.model.items.CopyHistory;
import ru.diaproject.vkplus.model.items.LikesInfo;
import ru.diaproject.vkplus.model.items.PostSourceInfo;
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
