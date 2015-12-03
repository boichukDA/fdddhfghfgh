package ru.diaproject.vkplus.vkcore.queries;


import java.util.HashMap;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.vkcore.user.VKUserConfiguration;

public abstract class VKQueryCore<T extends  VKDataCore> {
    private static final String VK_METHOD = "/method/method_name";
    private static final String VK_METHOD_REPLACE = "method_name";
    protected static final String VK_ACCESS_TOKEN = "access_token=%s";

    public static  <T extends VKDataCore> VKQueryCore<T> createInstance(VKUserConfiguration configuration){
        if (configuration.isAllowNoHttps())
            return new VKNoHttpsQueryCore<>(configuration);
        else return new VKHttpsQueryCore<>(configuration);
    }

    protected VKUserConfiguration configuration;

    protected VKQueryCore(VKUserConfiguration configuration){
        this.configuration = configuration;
    }

    protected StringBuilder sertificate(StringBuilder query){
        return  query.append(String.format(VK_ACCESS_TOKEN, configuration.getAccessToken()));
    }

    protected StringBuilder prepareQueryTemplate(StringBuilder builder) {
        return builder;
    }

    public  VKQuery<T> build(Class<T> resultType, VKQueryType queryType, VKQuerySubMethod subMethod, VKQueryResponseTypes responseType, HashMap<String, Object> params) throws VkQueryBuilderException{
        if (subMethod == null)
            throw new VkQueryBuilderException("Not set sub method");

        StringBuilder queryTemplate = new StringBuilder();
        queryTemplate = prepareQueryTemplate(queryTemplate);

        queryTemplate.append(VK_METHOD.replace(VK_METHOD_REPLACE, queryType.getValue()));
        queryTemplate.append(subMethod.getValue()).append(responseType.getValue()).append("?");
        if (!params.isEmpty())
            for (String key:params.keySet()) {
                queryTemplate.append(key);
                queryTemplate.append("=");
                queryTemplate.append(params.get(key));
                queryTemplate.append("&");

            } else queryTemplate.append("?");

        queryTemplate = sertificate(queryTemplate);
        return new VKQuery<>(resultType, queryTemplate, responseType);
    }
}
