package ru.diaproject.vkplus.model.baseitems;


import java.util.HashMap;

import ru.diaproject.vkplus.model.DataObject;
import ru.diaproject.vkplus.model.groups.IDataGroup;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.model.users.IDataUser;

public abstract class DataItem extends DataObject implements IDataItem{
    private Integer ownerId;
    private IDataOwner owner;

    @Override
    public IDataOwner getItemOwner() {
        return owner;
    }

    public void setOwner(IDataOwner owner) {
        this.owner = owner;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public void findOwner(HashMap<Integer, IDataUser> profiles,
                          HashMap<Integer, IDataGroup> groups){

        Integer positiveSourceId = Math.abs(getOwnerId());
        IDataOwner user = null;

        if (getOwnerId() > 0 && profiles.containsKey(getOwnerId()) )
            user = profiles.get(getOwnerId());
        else if (getOwnerId() < 0 && groups.containsKey(positiveSourceId) )
            user = groups.get(positiveSourceId);

        setOwner(user);
    }
}
