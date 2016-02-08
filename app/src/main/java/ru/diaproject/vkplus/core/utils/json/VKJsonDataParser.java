package ru.diaproject.vkplus.core.utils.json;

import org.json.JSONObject;
import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.model.IDataResult;
import ru.diaproject.vkplus.model.JsonResponseParser;
import ru.diaproject.vkplus.model.users.IDataObject;

public class VKJsonDataParser<T extends IDataObject> {
    private String filePath;
    private JsonHandler handler;

    public VKJsonDataParser(String filePath, Class<T> clazz) {
        this.filePath = filePath;

        JsonResponseParser annotation = clazz.getAnnotation(JsonResponseParser.class);
        if (annotation!=null) {
            Class<? extends JsonHandler> classHandler = annotation.jsonParser();
            try {
                handler = classHandler.newInstance();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public T parse() throws Exception {
        JSONObject object = new JSONObject(Utils.readStringDataFromFile(filePath));
        T result  = handler.parse(object);
        return result;
    }
}
