package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;

public abstract class AttachmentJsonParser {
    public abstract NewsEntityBase parse (JSONObject object, NewsEntityBase value) throws JSONException;
}
