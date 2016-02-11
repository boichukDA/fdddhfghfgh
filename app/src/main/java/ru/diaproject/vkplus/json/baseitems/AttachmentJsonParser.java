package ru.diaproject.vkplus.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.newsitems.DataMainItem;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;


public abstract class AttachmentJsonParser {
    public abstract IDataMainItem parse (JSONObject object, DataMainItem value) throws JSONException;
}
