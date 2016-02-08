package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.IDataObject;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;

public class VKItem<T extends IDataObject> {
    private VKQueryBuilder<T> queryBuilder;

    public VKItem(User user){
        queryBuilder = new VKQueryBuilder<>(user);
    }

    protected  VKQueryBuilder<T> getQueryBuilder(){
        return queryBuilder;
    }
}
