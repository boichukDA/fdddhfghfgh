package ru.diaproject.vkplus.core.utils.xml;

import android.util.Log;
import android.util.Xml;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import ru.diaproject.vkplus.core.VKDataCore;
import ru.diaproject.vkplus.core.utils.VKDataParser;

public class VKXmlDataParser<T extends VKDataCore> extends VKDataParser<T> {
    private String filePath;
    private Class<T> cls;
    private T response;

    public VKXmlDataParser(String filePath, Class<T> clazz) {
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

    public T parse() {
        try {
            Log.e("parser", String.valueOf((response==null)?true:false));
            Xml.parse(new FileReader(filePath), (response).getXmlHandler());
            return response;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}

