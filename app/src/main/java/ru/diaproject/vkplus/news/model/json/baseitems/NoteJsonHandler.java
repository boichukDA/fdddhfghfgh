package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.NewsEntityBase;
import ru.diaproject.vkplus.news.model.baseitems.NewsNoteItem;
import ru.diaproject.vkplus.news.model.items.Notes;
import ru.diaproject.vkplus.news.model.json.items.NotesJsonHandler;

public class NoteJsonHandler extends AttachmentJsonParser {
    @Override
    public NewsNoteItem parse(JSONObject object, NewsEntityBase value) throws JSONException {
        JSONObject obj = object.getJSONObject("notes");
        NotesJsonHandler handler = new NotesJsonHandler();
        Notes notes = handler.parse(obj);
        value.setAttachments(notes);
        return (NewsNoteItem) value;
    }
}
