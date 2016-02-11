package ru.diaproject.vkplus.model.newsitems;


public class DataNoteItem extends DataMainItem {
    @Override
    public final FilterType getType() {
        return FilterType.NOTE;
    }
}
