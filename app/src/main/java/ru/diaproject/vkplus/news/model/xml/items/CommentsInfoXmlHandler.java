/**
 * *************************************************************************
 * *
 * Copyright (C) 2014-2015 iBuildApp, Inc. ( http://ibuildapp.com )         *
 * *
 * This file is part of iBuildApp.                                          *
 * *
 * This Source Code Form is subject to the terms of the iBuildApp License.  *
 * You can obtain one at http://ibuildapp.com/license/                      *
 * *
 * **************************************************************************
 */
package ru.diaproject.vkplus.news.model.xml.items;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

import org.xml.sax.Attributes;


import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.items.CommentsInfo;

public class CommentsInfoXmlHandler {
    private CommentsInfo currentCommentInfo;
    private NewsPostItem response;
    public CommentsInfoXmlHandler(){

    }
    public void setXmlContentHandler(Element root) {
        Element commentsInfoElement = root.getChild("comments");

        commentsInfoElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                currentCommentInfo = new CommentsInfo();
            }
        });

        commentsInfoElement.getChild("count").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentCommentInfo.setCount(Integer.parseInt(body.trim()));
            }
        });

        commentsInfoElement.getChild("can_post").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentCommentInfo.setCanPost(new Integer(1).equals(Integer.parseInt(body.trim())) ? true : false);
            }
        });

        commentsInfoElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                response.setComments(currentCommentInfo);
                currentCommentInfo = null;
            }
        });
        //END COMMENT_INFO INITIALIZATION
    }

    public void setResponse(NewsPostItem response) {
        this.response = response;
    }
}
