package ru.diaproject.vkplus.news.model;

import org.xml.sax.ContentHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.core.utils.json.JsonHandler;
import ru.diaproject.vkplus.news.IDataFilter;
import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.json.ResponseJsonHandler;
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.news.model.xml.ResponseXmlHandler;

public class Response implements VKDataCore {
    private List<NewsEntityBase> items;
    private HashMap<Integer, User> profiles;
    private ResponseXmlHandler handler;
    private ResponseJsonHandler jsonHandler;
    private HashMap<Integer, Group> groups;
    private String nextFrom;
    private String previousNextFrom;

    public Response(){
        handler = new ResponseXmlHandler(this);
        jsonHandler = new ResponseJsonHandler(this);
    }
    public List<NewsEntityBase> getItems() {
        return items;
    }

    public void setItems(List<NewsEntityBase> items) {
        this.items = items;
    }

    @Override
    public ContentHandler getXmlHandler() {
        return handler.getContentHandler();
    }

    @Override
    public JsonHandler<Response> getJsonHandler() {
        return jsonHandler;
    }

    public HashMap<Integer, User> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<Integer, User> profiles) {
        this.profiles = profiles;
    }

    public void setGroups(HashMap<Integer, Group> groups) {
        this.groups = groups;
    }

    public HashMap<Integer, Group> getGroups() {
        return groups;
    }

    public void setNextFrom(String nextFrom) {
        this.nextFrom = nextFrom;
    }

    public String getNextFrom() {
        return nextFrom;
    }

    public boolean hasNextDataPage(){
        return !"".equals(nextFrom);
    }

    public void addNewPartData(Response result) {
       /* HashSet<NewsEntityBase> newItems = new HashSet<>(this.items);
        newItems.addAll(result.getItems());

        this.items = new ArrayList<>(newItems);*/

        this.items.addAll(result.items);
        this.profiles.putAll(result.getProfiles());
        this.groups.putAll(result.getGroups());
        this.previousNextFrom = nextFrom;
        this.nextFrom = result.getNextFrom();
    }

    public Response applyFilter (IDataFilter filter){
        Response newResonse = new Response();
        List<NewsEntityBase> resultList = new ArrayList<>();
        for (NewsEntityBase item: items){
            if (filter.apply(item, profiles, groups))
                resultList.add(item);
        }
        newResonse.setGroups(groups);
        newResonse.setItems(resultList);
        newResonse.setProfiles(profiles);
        newResonse.setNextFrom(nextFrom);
        return newResonse;
    }

    public String getPreviousNextFrom() {
        return previousNextFrom;
    }
}
