package ru.diaproject.vkplus.news.model.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.news.model.baseitems.DataMainItem;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.items.Notes;
import ru.diaproject.vkplus.news.model.json.items.NotesJsonHandler;

public class NoteJsonHandler extends AttachmentJsonParser {
    @Override
    public IDataMainItem parse(JSONObject object, DataMainItem value) throws JSONException {
        JSONObject obj = object.getJSONObject("notes");
        NotesJsonHandler handler = new NotesJsonHandler();
        Notes notes = handler.parse(obj);
        value.setNotes(notes.getNotes());
        return value;
    }
}
