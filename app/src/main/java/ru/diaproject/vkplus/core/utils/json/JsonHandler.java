package ru.diaproject.vkplus.core.utils.json;


import org.json.JSONObject;

import java.io.Serializable;

import ru.diaproject.vkplus.model.IDataResult;

public interface JsonHandler extends Serializable {
     <T> T parse(JSONObject object);
}
