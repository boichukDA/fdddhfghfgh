package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.DataMainItem;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;


public abstract class AttachmentJsonParser {
    public abstract IDataMainItem parse (JSONObject object, DataMainItem value) throws JSONException;
}
