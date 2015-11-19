package ru.diaproject.vkplus.news.model.xml.items;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.items.Notes;
import ru.diaproject.vkplus.news.model.items.NotesInfo;

public class NotesXmlHandler {
    private Notes currentNotes;
    private NewsPostItem response;
    private List<NotesInfo> notesInfos;

    public NotesXmlHandler(){
        notesInfos = new ArrayList<>();
    }
    public void setXmlContentHandler(Element root) {
        Element noteElement = root.getChild("notes");

        final NotesInfoXmlHandler notesInfoXmlHandler = new NotesInfoXmlHandler();
        notesInfoXmlHandler.setXmlContentHandler(noteElement);
        notesInfoXmlHandler.setResponse(notesInfos);

        noteElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                currentNotes = new Notes();

            }
        });

        noteElement.getChild("count").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentNotes.setCount(Integer.parseInt(body.trim()));
            }
        });

        noteElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                currentNotes.setNotes(notesInfos);
               // response.setNotes(currentNotes);
            }
        });
    }

    public void setResponse(NewsPostItem response) {
        this.response = response;
    }
}
