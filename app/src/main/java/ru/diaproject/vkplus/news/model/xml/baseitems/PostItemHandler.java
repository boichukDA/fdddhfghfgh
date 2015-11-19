package ru.diaproject.vkplus.news.model.xml.baseitems;

import android.sax.Element;
import android.sax.EndTextElementListener;

import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.baseitems.PostType;

public class PostItemHandler {
    private NewsPostItem currentItem;
    public PostItemHandler(){

    }
    public void setXmlContentHandler(Element itemElement) {
        itemElement.getChild("copy_owner_id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setCopyOwnerId(Integer.parseInt(body.trim()));
            }
        });

        itemElement.getChild("copy_post_id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setCopyPostId(Integer.parseInt(body.trim()));
            }
        });

        itemElement.getChild("post_type").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setPostType(PostType.valueOf(body.trim().toUpperCase()));
            }
        });
        itemElement.getChild("text").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setText(body.trim());
            }
        });
    }

    public void setProcessEntity(NewsPostItem processEntity) {
        this.currentItem = processEntity;
    }
}
