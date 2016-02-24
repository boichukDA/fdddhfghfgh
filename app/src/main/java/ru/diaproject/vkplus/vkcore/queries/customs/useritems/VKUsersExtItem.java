package ru.diaproject.vkplus.vkcore.queries.customs.useritems;




import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;

public class VKUsersExtItem extends VKItem<DataUserExt>{
    public VKUsersExtItem(User user) {
        super(user);
        getQueryBuilder().setVKQueryType(VKQueryType.USERS);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(DataUserExt.class);
    }

    public VKPreparedItem<DataUserExt> get(Integer userId){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.GET);
        getQueryBuilder().addCondition(VKParameter.USER_IDS.getValue(), userId);
        return new PreparedItem<>(getQueryBuilder());
    }
}
