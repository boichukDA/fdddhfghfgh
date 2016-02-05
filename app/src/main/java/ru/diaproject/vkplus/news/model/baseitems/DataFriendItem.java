package ru.diaproject.vkplus.news.model.baseitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.diaproject.vkplus.news.model.groups.IDataGroup;
import ru.diaproject.vkplus.news.model.items.Friends;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.model.users.IDataUser;

public class DataFriendItem extends DataMainItem {
    private List<IDataOwner> friendOwners;

    public DataFriendItem(){
        friendOwners = new ArrayList<>();
    }

    @Override
    public final FilterType getType() {
        return FilterType.FRIEND;
    }

    @Override
    public void findOwner(HashMap<Integer, IDataUser> profiles, HashMap<Integer, IDataGroup> groups) {
        super.findOwner(profiles, groups);

        Friends friends = getAttachmentFriends();
        if (friends!= null){
            List<IDataOwner> owners = new ArrayList<>();
            for (Integer friend : friends.getFriends()){
                owners.add(getOwner(friend, profiles, groups));
            }
            setFriendOwners(owners);
        }
    }

    public IDataOwner getOwner(Integer id, HashMap<Integer, IDataUser> profiles, HashMap<Integer, IDataGroup> groups){
        Integer positiveSourceId = Math.abs(id);
        IDataOwner user = null;

        if (id > 0 && profiles.containsKey(id) )
            user = profiles.get(id);
        else if (id < 0 && groups.containsKey(positiveSourceId) )
            user = groups.get(positiveSourceId);

        return user;
    }

    public List<IDataOwner> getFriendOwners() {
        return friendOwners;
    }

    public void setFriendOwners(List<IDataOwner> friendOwners) {
        this.friendOwners = friendOwners;
    }
}
