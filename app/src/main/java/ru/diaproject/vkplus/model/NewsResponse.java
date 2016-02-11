package ru.diaproject.vkplus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.diaproject.vkplus.news.IDataFilter;
import ru.diaproject.vkplus.model.attachments.audios.AudioInfo;
import ru.diaproject.vkplus.model.attachments.VideoInfo;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;
import ru.diaproject.vkplus.model.groups.IDataGroup;
import ru.diaproject.vkplus.json.ResponseJsonHandler;
import ru.diaproject.vkplus.model.users.DataOwner;
import ru.diaproject.vkplus.model.users.IDataObject;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.model.users.IDataUser;

@JsonResponseParser(jsonParser = ResponseJsonHandler.class)
public class NewsResponse implements IDataResult, IDataObject, Serializable{
    private List<IDataMainItem> items;
    private HashMap<Integer, IDataUser> profiles;
    private HashMap<Integer, IDataGroup> groups;
    private String nextFrom;
    private String previousNextFrom;

    public NewsResponse(){
    }

    public List<IDataMainItem> getListItems() {
        return items;
    }

    public void setItems(List<IDataMainItem> items) {
        this.items = items;
    }

    public HashMap<Integer, IDataUser> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<Integer, IDataUser> profiles) {
        this.profiles = profiles;
    }

    public void setGroups(HashMap<Integer, IDataGroup> groups) {
        this.groups = groups;
    }

    public HashMap<Integer, IDataGroup> getGroups() {
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

    public void addNewPartData(NewsResponse result) {
        this.items.addAll(result.items);
        this.profiles.putAll(result.getProfiles());
        this.groups.putAll(result.getGroups());
        this.previousNextFrom = nextFrom;
        this.nextFrom = result.getNextFrom();
    }

    public NewsResponse applyFilter (IDataFilter filter){
        NewsResponse newResonse = new NewsResponse();
        List<IDataMainItem> resultList = new ArrayList<>();
        for (IDataMainItem item: items){
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

    @Override
    public Integer getId() {
        return 0;
    }

    public void prepareItems(){
        for (int index = 0; index < items.size(); index++ ) {
            IDataMainItem item = items.get(index);
            item.findOwner(profiles, groups);

            if (item instanceof IDataResult)
                ((IDataResult) item).prepareItems();

            if (item.getAttachmentVideos()!=null && !item.getAttachmentVideos().isEmpty())
                for (VideoInfo video:item.getAttachmentVideos())
                    video.prepareItems();

            if (item.getAttachmentAudios()!=null && !item.getAttachmentAudios().isEmpty())
                for (AudioInfo audioInfo:item.getAttachmentAudios())
                    audioInfo.prepareItems();
        }

        for (IDataOwner owner: profiles.values()){
            ((DataOwner)owner).prepareItems();
        }

        for (IDataOwner owner: groups.values()){
            ((DataOwner)owner).prepareItems();
        }
    }
}
