package ru.diaproject.vkplus.vkcore.queries.customs.useritems;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;

public class VKUsersItem extends VKItem<IDataUser> {
    public VKUsersItem(User user) {
        super(user);
        getQueryBuilder().setVKQueryType(VKQueryType.USERS);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(IDataUser.class);
    }

    public VKPreparedItem<IDataUser> get(){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.GET);
        return new PreparedItem<>(getQueryBuilder());
    }
    public VKPreparedItem<IDataUser> getById(Integer id){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.GET);
        getQueryBuilder().addCondition(VKParameter.USER_IDS.getValue(), id);
        return new PreparedItem<>(getQueryBuilder());
    }
}
