package ru.diaproject.vkplus.vkcore.queries;


import java.util.HashMap;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.vkcore.user.VKUserConfiguration;

public class VKQueryBuilder<T extends VKDataCore> {

    private VKQuerySubMethod subMethod = null;
    private VKQueryType queryType = null;
    private VKQueryResponseTypes responseType = null;
    private VKQueryCore core;
    private Class<T> resultType;
    private HashMap<String, Object> params;

    public VKQueryBuilder( VKUserConfiguration configuration){
        core = VKQueryCore.createInstance(configuration);
        params = new HashMap<>();
        params.put("v", "5.37");
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

    public VKQuery build() throws VkQueryBuilderException{
       return core.build(resultType, queryType, subMethod,responseType,params);
    }
}
