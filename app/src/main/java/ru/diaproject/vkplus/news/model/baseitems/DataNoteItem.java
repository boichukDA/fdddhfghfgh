package ru.diaproject.vkplus.news.model.baseitems;


public class DataNoteItem extends DataMainItem {
    @Override
    public final FilterType getType() {
        return FilterType.NOTE;
    }
}
