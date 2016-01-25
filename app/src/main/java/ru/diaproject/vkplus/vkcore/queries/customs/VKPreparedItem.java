package ru.diaproject.vkplus.vkcore.queries.customs;


import ru.diaproject.vkplus.news.model.users.IDataObject;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;

public interface VKPreparedItem <T extends IDataObject> {
    VKPreparedItem<T> with(VKParameter key, Object value);

    VKPreparedItem<T> and(VKParameter key, Object value);

    VKQuery<T> build();
}
