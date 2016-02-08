package ru.diaproject.vkplus.vkcore.queries;


import java.util.HashMap;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.IDataObject;

public abstract class VKQueryCore<T extends IDataObject> {
    private static final String VK_METHOD = "/method/method_name";
    private static final String VK_METHOD_REPLACE = "method_name";
    protected static final String VK_ACCESS_TOKEN = "access_token=%s";

    public static  <T extends IDataObject> VKQueryCore<T> createInstance(User user){
        if (user.getMainConfiguration().isHttpsRequired())
            return new VKNoHttpsQueryCore<>(user);
        else return new VKHttpsQueryCore<>(user);
    }

    protected User user;

    protected VKQueryCore(User user){
        this.user = user;
    }

    protected StringBuilder sertificate(StringBuilder query){
        return  query.append(String.format(VK_ACCESS_TOKEN, user.getAccessToken()));
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
