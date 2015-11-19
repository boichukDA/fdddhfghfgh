package ru.diaproject.vkplus.core.utils;


import ru.diaproject.vkplus.core.VKDataCore;

public abstract class VKDataParser<T extends VKDataCore> {

    public abstract T parse() throws  Exception;

}
