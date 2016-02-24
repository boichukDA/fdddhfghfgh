package ru.diaproject.vkplus.vkcore.queries.customs.useritems;


import java.util.List;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.model.users.DataUserList;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.vkcore.queries.customs.ResponseManager;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;

public class UserManager extends ResponseManager {

    public UserManager(User user) {
        super(user);
    }

    public VKPreparedItem<IDataUser> get(){
        VKUsersItem item = new VKUsersItem(getUser());
        return item.get();
    }

    public VKPreparedItem<IDataUser> getById(Integer id){
        VKUsersItem item = new VKUsersItem(getUser());
        return item.getById(id);
    }

    public VKPreparedItem<DataUserExt> getInfo(Integer id){
        VKUsersExtItem item = new VKUsersExtItem(getUser());
        return item.get(id);
    }

    public VKPreparedItem<DataUserList> getAll(List<Integer> list) {
        VKUserListItem item = new VKUserListItem(getUser());
        return item.getAll(list);
    }
}
