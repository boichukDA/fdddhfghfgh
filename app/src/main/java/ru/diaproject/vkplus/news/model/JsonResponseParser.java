package ru.diaproject.vkplus.news.model;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ru.diaproject.vkplus.core.utils.json.JsonHandler;


@Target(value= ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface JsonResponseParser {
    Class<? extends JsonHandler> jsonParser();
}
