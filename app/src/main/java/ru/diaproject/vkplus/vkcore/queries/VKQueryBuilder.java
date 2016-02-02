package ru.diaproject.vkplus.vkcore.queries;


import java.util.HashMap;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.news.model.users.IDataObject;

public class VKQueryBuilder<T extends IDataObject> {
    private static final String VK_QUERY_VERSION = "5.44";

    private VKQuerySubMethod subMethod = null;
    private VKQueryType queryType = null;
    private VKQueryResponseTypes responseType = null;
    private VKQueryCore<T> core;
    private Class<T> resultType;
    private HashMap<String, Object> params;

    public VKQueryBuilder( User user){
        core = VKQueryCore.createInstance(user);
        params = new HashMap<>();
        params.put("v", VK_QUERY_VERSION);
        responseType = VKQueryResponseTypes.JSON;
    }
    public VKQueryBuilder setVKQueryType(VKQueryType queryType){
        this.queryType  = queryType;
        return this;
    }

    public VKQueryBuilder setVKResultType(Class<T> resultType){
        this.resultType  = resultType;
        return this;
    }

    public VKQueryBuilder setResultFormatType(VKQueryResponseTypes type){
        responseType = type;
        return this;
    }

    public VKQueryBuilder setVKMethod(VKQuerySubMethod subMethod){
        this.subMethod = subMethod;
        return this;
    }
    public VKQueryBuilder addCondition(String name, Object value){
        params.put(name, value);
        return this;
    }

    public VKQuery<T> build() throws VkQueryBuilderException{
       return core.build(resultType, queryType, subMethod,responseType,params);
    }
}
