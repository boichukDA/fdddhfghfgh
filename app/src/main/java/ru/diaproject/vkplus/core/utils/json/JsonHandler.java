package ru.diaproject.vkplus.core.utils.json;

import org.json.JSONObject;

import ru.diaproject.vkplus.core.VKDataCore;

public interface JsonHandler<T extends VKDataCore> {
     T parse(JSONObject object);
}
