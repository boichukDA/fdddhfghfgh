package ru.diaproject.vkplus.news.model.xml.baseitems;

import android.sax.Element;
import android.sax.EndTextElementListener;

import ru.diaproject.vkplus.news.model.baseitems.NewsFriendItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.baseitems.PostType;

public class FriendItemHandler {
    private NewsFriendItem currentItem;
    public FriendItemHandler(){

    }
    public void setXmlContentHandler(Element itemElement) {

    }

    public void setProcessEntity(NewsFriendItem processEntity) {
        this.currentItem = processEntity;
    }
}
