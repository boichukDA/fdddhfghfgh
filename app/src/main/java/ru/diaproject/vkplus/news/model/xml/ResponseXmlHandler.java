package ru.diaproject.vkplus.news.model.xml;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.RootElement;
import org.xml.sax.ContentHandler;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.model.xml.baseitems.BaseItemHandler;

public class ResponseXmlHandler {
    private Response response;
    private List<NewsEntityBase> items;

    public ResponseXmlHandler(Response response){
        this.response = response;
        items = new ArrayList<>();
    }
    public ContentHandler getContentHandler(){
        RootElement root = new RootElement("response");
        Element itemsElement = root.getChild("items");

        BaseItemHandler itemHandler = new BaseItemHandler();
        itemHandler.setXmlContentHandler(itemsElement);
        itemHandler.setResponse(items);

        itemsElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                response.setItems(items);
            }
        });
        return root.getContentHandler();
    }
}
