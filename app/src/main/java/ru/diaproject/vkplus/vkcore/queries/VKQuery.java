package ru.diaproject.vkplus.vkcore.queries;


import ru.diaproject.vkplus.news.model.users.IDataObject;

public class VKQuery<T extends IDataObject> {
    private StringBuilder builder;
    private VKQueryResponseTypes responseType;
    private Class<T> resultType;

    public VKQuery(Class<T> resultType, StringBuilder queryTemplate, VKQueryResponseTypes type) {
        this.resultType = resultType;
        builder = queryTemplate;
        responseType = type;
    }

    public String getQuery(){
        return builder.toString();
    }

    public VKQueryResponseTypes getResponseType() {
        return responseType;
    }

    public Class<T> getResultType() {
        return resultType;
    }
}
