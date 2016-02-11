package ru.diaproject.vkplus.core.utils.json;

import org.json.JSONObject;

import java.lang.reflect.Method;

import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.model.IDataResult;
import ru.diaproject.vkplus.model.JsonResponseParser;
import ru.diaproject.vkplus.model.users.IDataObject;

public class VKJsonDataParser<T extends IDataObject> {
    private String filePath;
    private JsonHandler handler;
    private Method parseMethod;

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
        }else {
            try {
                parseMethod = clazz.getMethod("parseObject", JSONObject.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public T parse() throws Exception {
        JSONObject object = new JSONObject(Utils.readStringDataFromFile(filePath));
        T result = null;
        if (handler != null)
         result  = handler.parse(object);
        else {
            try {
                result = (T) parseMethod.invoke(null, object);
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
