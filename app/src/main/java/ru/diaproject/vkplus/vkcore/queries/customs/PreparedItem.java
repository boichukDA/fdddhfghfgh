package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.model.users.IDataObject;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;
import ru.diaproject.vkplus.vkcore.queries.VkQueryBuilderException;

public class PreparedItem<T extends IDataObject> implements VKPreparedItem<T > {

    private VKQueryBuilder<T> preparedQuery;

    public PreparedItem(VKQueryBuilder<T> preparedQuery){
        this.preparedQuery = preparedQuery;
    }


    @Override
    public VKPreparedItem<T> with(VKParameter key, Object value) {
        preparedQuery.addCondition(key.getValue(), value);
        return this;
    }

    @Override
    public VKPreparedItem<T> and(VKParameter key, Object value) {
        preparedQuery.addCondition(key.getValue(), value);
        return this;
    }

    @Override
    public VKQuery<T> build() {
        VKQuery<T> query = null;
        try {
            query =  preparedQuery.build();
        } catch (VkQueryBuilderException e) {
            e.printStackTrace();
        }
        return query;
    }
}
