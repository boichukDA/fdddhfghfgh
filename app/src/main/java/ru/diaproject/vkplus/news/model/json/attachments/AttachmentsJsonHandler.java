package ru.diaproject.vkplus.news.model.json.attachments;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.news.model.attachments.Attachment;
import ru.diaproject.vkplus.news.model.attachments.Attachments;

public class AttachmentsJsonHandler {
    public Attachments parse(JSONArray attachmentsJsonObject) throws JSONException {
        List<Attachment> attes = new ArrayList<>();
        Attachments attachments = new Attachments();
        for (int index = 0; index < attachmentsJsonObject.length(); index++){
            AttachmentJsonHandler handler = new AttachmentJsonHandler();
            Attachment attachment = handler.parse(attachmentsJsonObject.getJSONObject(index));
            attes.add(attachment);
        }
        attachments.setAttachments(attes);
     return attachments;
    }
}
