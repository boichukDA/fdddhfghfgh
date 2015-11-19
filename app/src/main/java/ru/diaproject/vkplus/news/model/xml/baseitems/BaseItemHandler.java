package ru.diaproject.vkplus.news.model.xml.baseitems;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;

import java.util.List;

import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsFriendItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsPhotoTagItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.baseitems.NewsWallPhotoItem;

public class BaseItemHandler {
    private NewsEntityBase currentItem;
    private List<NewsEntityBase> response;
    public BaseItemHandler(){

    }
    public void setXmlContentHandler(Element root) {
        final Element itemElement = root.getChild("item");

        final PostItemHandler postHandler = new PostItemHandler();
        postHandler.setXmlContentHandler(itemElement);

        final PhotoItemHandler photoHandler = new PhotoItemHandler();
        photoHandler.setXmlContentHandler(itemElement);

        final PhotoTagItemHandler photoTagHandler = new PhotoTagItemHandler();
        photoTagHandler.setXmlContentHandler(itemElement);

        final FriendItemHandler friendHandler = new FriendItemHandler();
        friendHandler.setXmlContentHandler(itemElement);

        itemElement.getChild("type").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                FilterType filterType = FilterType.valueOf(body.trim().toUpperCase());
                switch (filterType) {
                    case POST:
                        currentItem = new NewsPostItem();
                        break;

                    case FRIEND:
                        currentItem = new NewsFriendItem();
                        break;

                    case WALL_PHOTO:
                        currentItem = new NewsWallPhotoItem();
                        break;

                    case PHOTO:
                        currentItem = new NewsPhotoItem();
                        photoHandler.setProcessEntity( currentItem);
                        break;

                    case PHOTO_TAG:
                        currentItem = new NewsPhotoTagItem();
                        break;
                }
            }
        });

        itemElement.getChild("source_id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setSourceId(Integer.parseInt(body.trim()));
            }
        });

        itemElement.getChild("date").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setDate(Integer.parseInt(body.trim()));
            }
        });

        itemElement.getChild("final_post").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setFinalPost(1);// TODO:������� ���������
            }
        });

        itemElement.getChild("can_edit").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setCanEdit(new Integer(1).equals(Integer.parseInt(body.trim())) ? true : false);
            }
        });

        itemElement.getChild("can_delete").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentItem.setCanDelete(new Integer(1).equals(Integer.parseInt(body.trim())) ? true : false);
            }
        });

        itemElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                switch(currentItem.getType()){
                    case POST:
                        postHandler.setProcessEntity((NewsPostItem) currentItem);
                        break;

                    case FRIEND:
                        friendHandler.setProcessEntity((NewsFriendItem) currentItem);
                        break;

                    case WALL_PHOTO:
                        photoHandler.setProcessEntity( currentItem);
                        break;

                    case PHOTO:
                        photoHandler.setProcessEntity(currentItem);
                        break;

                    case PHOTO_TAG:
                        photoTagHandler.setProcessEntity((NewsPhotoTagItem) currentItem);
                        break;
                }
                response.add(currentItem);
                //currentItem = null;
            }
        });
    }

    public void setResponse(List<NewsEntityBase> response) {
        this.response = response;
    }

    public List<NewsEntityBase> getResponse() {
        return response;
    }
}
