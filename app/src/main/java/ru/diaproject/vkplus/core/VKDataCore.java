package ru.diaproject.vkplus.core;


import org.xml.sax.ContentHandler;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;

public interface VKDataCore {
    ContentHandler getXmlHandler();
    JsonHandler getJsonHandler();
}
