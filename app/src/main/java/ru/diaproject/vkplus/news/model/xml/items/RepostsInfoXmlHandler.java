package ru.diaproject.vkplus.news.model.xml.items;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;

import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.items.RepostsInfo;

public class RepostsInfoXmlHandler {
    private RepostsInfo currentRepostsInfo;
    private NewsPostItem response;

    public void setXmlContentHandler(Element root){
        Element repostsInfoElement = root.getChild("reposts");
        repostsInfoElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                currentRepostsInfo = new RepostsInfo();
            }
        });

        repostsInfoElement.getChild("count").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentRepostsInfo.setCount(Integer.parseInt(body.trim()));
            }
        });

        repostsInfoElement.getChild("user_reposted").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentRepostsInfo.setUserReposted(new Integer(1).equals(Integer.parseInt(body.trim())) ? true : false);
            }
        });

        repostsInfoElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                response.setReposts(currentRepostsInfo);
                currentRepostsInfo = null;
            }
        });
    }

    public void setResponse(NewsPostItem response) {
        this.response = response;
    }
}
