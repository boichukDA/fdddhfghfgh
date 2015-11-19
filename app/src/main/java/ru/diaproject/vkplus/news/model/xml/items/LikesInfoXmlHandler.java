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
import ru.diaproject.vkplus.news.model.items.LikesInfo;

public class LikesInfoXmlHandler {
    private LikesInfo currentLikesInfo;
    private NewsPostItem response;

    public void setXmlContentHandler(Element root){
        Element likesInfoElement = root.getChild("likes");
        likesInfoElement.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                currentLikesInfo = new LikesInfo();
            }
        });

        likesInfoElement.getChild("count").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentLikesInfo.setCount(Integer.parseInt(body.trim()));
            }
        });

        likesInfoElement.getChild("user_likes").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentLikesInfo.setUserLikes(new Integer(1).equals(Integer.parseInt(body.trim())) ? true : false);
            }
        });

        likesInfoElement.getChild("can_like").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentLikesInfo.setCanLikes(new Integer(1).equals(Integer.parseInt(body.trim())) ? true : false);
            }
        });

        likesInfoElement.getChild("can_publish").setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentLikesInfo.setCanPublish(new Integer(1).equals(Integer.parseInt(body.trim())) ? true : false);
            }
        });
        likesInfoElement.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                response.setLikes(currentLikesInfo);
                currentLikesInfo = null;
            }
        });
    }

    public void setResponse(NewsPostItem response) {
        this.response = response;
    }
}
