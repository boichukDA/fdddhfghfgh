package ru.diaproject.vkplus.vkcore.queries.customs.items;

import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.users.IDataUser;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class VKUsersItem extends VKItem<IDataUser> {
    public VKUsersItem(VKUser user) {
        super(user);
        getQueryBuilder().setVKQueryType(VKQueryType.USERS);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(IDataUser.class);
    }

    public VKPreparedItem<IDataUser> get(){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.DEFAULT);
        return new PreparedItem<>(getQueryBuilder());
    }

}
