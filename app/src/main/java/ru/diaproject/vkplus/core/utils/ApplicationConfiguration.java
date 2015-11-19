package ru.diaproject.vkplus.core.utils;

/**
 * Created by minberg on 23.07.2015.
 */
public enum  ApplicationConfiguration  {

    INSTANCE;

    private ColorScheme defaultColorScheme;

    ApplicationConfiguration(){
        defaultColorScheme = ColorScheme.VK_CLASSIC;
    }
    public ColorScheme getColorScheme(){
        return defaultColorScheme;
    }
}
