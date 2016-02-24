package ru.diaproject.vkplus.vkcore.queries.customs.useritems;


import java.util.List;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.DataUserList;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;

public class VKUserListItem extends VKItem<DataUserList> {

    public VKUserListItem(User user) {
        super(user);
        getQueryBuilder().setVKQueryType(VKQueryType.USERS);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(DataUserList.class);
    }

    public VKPreparedItem<DataUserList> getAll(List<Integer> list) {
        getQueryBuilder().setVKMethod(VKQuerySubMethod.GET);
        getQueryBuilder().addCondition(VKParameter.USER_IDS.getValue(), idsToString(list));

        return new PreparedItem<>(getQueryBuilder());
    }

    private String idsToString(List<Integer> ids){

        StringBuilder idsStringBuilder = new StringBuilder("");
        for (Integer id:ids){
            idsStringBuilder.append(id);
            idsStringBuilder.append(",");
        }
        idsStringBuilder.deleteCharAt(idsStringBuilder.length() - 1);

        return idsStringBuilder.toString();
    }
}
