package ru.diaproject.vkplus.vkcore.queries.customs.newsitems;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.NewsResponse;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;

public  class VKNewsItem extends VKItem<NewsResponse> {

    public VKNewsItem(User user) {
        super(user);
        getQueryBuilder().setVKQueryType(VKQueryType.NEWS);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(NewsResponse.class);
    }

    public VKPreparedItem<NewsResponse> get(){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.GET);
        return new PreparedItem<>(getQueryBuilder());
    }

    public VKPreparedItem<NewsResponse> recommended() {
        getQueryBuilder().setVKMethod(VKQuerySubMethod.RECOMENDED);
        return new PreparedItem<>(getQueryBuilder());
    }
}