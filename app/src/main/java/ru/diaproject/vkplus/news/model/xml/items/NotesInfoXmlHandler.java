package ru.diaproject.vkplus.news.model.xml.items;


import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;

import java.util.List;

import ru.diaproject.vkplus.news.model.items.NotesInfo;

//TODO:Not done Comments tag

public class NotesInfoXmlHandler {
    private NotesInfo notesInfo;
    private List<NotesInfo> response;

    public void setXmlContentHandler(Element root) {
        Element photosInfoElement = root.getChild("note");
        photosInfoElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                notesInfo = new NotesInfo();
            }
        });

        photosInfoElement.getChild("id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                notesInfo.setId(Integer.parseInt(body.trim()));
            }
        });

        photosInfoElement.getChild("title").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                notesInfo.setTitle(body.trim());
            }
        });

        photosInfoElement.getChild("owner_id").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                notesInfo.setOwnerId(Integer.parseInt(body.trim()));
            }
        });

        photosInfoElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                response.add(notesInfo);
                notesInfo = null;
            }
        });
    }

    public void setResponse(List<NotesInfo> response) {
        this.response = response;
    }
}
