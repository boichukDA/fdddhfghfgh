package ru.diaproject.vkplus.json.baseitems;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.newsitems.DataMainItem;
import ru.diaproject.vkplus.model.newsitems.IDataMainItem;
import ru.diaproject.vkplus.model.attachments.notes.Notes;
import ru.diaproject.vkplus.json.items.NotesJsonHandler;

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
