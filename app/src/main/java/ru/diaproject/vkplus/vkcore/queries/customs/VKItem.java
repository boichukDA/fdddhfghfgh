package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.news.model.users.IDataObject;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class VKItem<T extends IDataObject> {
    private VKQueryBuilder<T> queryBuilder;

    public VKItem(VKUser user){
        queryBuilder = new VKQueryBuilder<>(user.getConfiguration());
    }

    protected  VKQueryBuilder<T> getQueryBuilder(){
        return queryBuilder;
    }
}
