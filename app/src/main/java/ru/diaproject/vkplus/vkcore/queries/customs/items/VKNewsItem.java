package ru.diaproject.vkplus.vkcore.queries.customs.items;

import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.customs.PreparedItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKItem;
import ru.diaproject.vkplus.vkcore.queries.customs.VKPreparedItem;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public  class VKNewsItem extends VKItem<NewsResponse> {

    public VKNewsItem(VKUser user) {
        super(user);
        getQueryBuilder().setVKQueryType(VKQueryType.NEWS);
        getQueryBuilder().setResultFormatType(VKQueryResponseTypes.JSON);
        getQueryBuilder().setVKResultType(NewsResponse.class);
    }

    public VKPreparedItem<NewsResponse> get(){
        getQueryBuilder().setVKMethod(VKQuerySubMethod.DEFAULT);
        return new PreparedItem<>(getQueryBuilder());
    }

    public VKPreparedItem<NewsResponse> recommended() {
        getQueryBuilder().setVKMethod(VKQuerySubMethod.RECOMENDED);
        return new PreparedItem<>(getQueryBuilder());
    }
}