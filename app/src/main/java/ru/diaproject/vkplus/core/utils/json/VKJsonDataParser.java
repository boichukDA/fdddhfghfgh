package ru.diaproject.vkplus.core.utils.json;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.core.utils.Utils;
import ru.diaproject.vkplus.core.utils.VKDataParser;

public class VKJsonDataParser<T extends VKDataCore> extends VKDataParser<T> {
    private String filePath;
    private Class<T> cls;
    private T response;

    public VKJsonDataParser(String filePath, Class<T> clazz) {
        this.filePath = filePath;
        this.cls = clazz;
        try {
            response = cls.getConstructor().newInstance();
        } catch (InstantiationException e) {
            Log.e("parser", "exception", e);
        } catch (IllegalAccessException e) {
            Log.e("parser", "exception", e);
        } catch (InvocationTargetException e) {
            Log.e("parser", "exception", e);
        } catch (NoSuchMethodException e) {
            Log.e("parser", "exception", e);
        }
    }

    @Override
    public T parse() throws Exception {
        Log.e("parser", String.valueOf((response == null) ? true : false));
        JSONObject object = new JSONObject(Utils.readStringDataFromFile(filePath));
        JsonHandler handler = response.getJsonHandler();
        response = (T) handler.parse(object);
        return response;
    }
}
