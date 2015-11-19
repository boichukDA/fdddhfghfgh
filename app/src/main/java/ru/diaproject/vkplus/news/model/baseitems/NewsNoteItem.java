package ru.diaproject.vkplus.news.model.baseitems;

import ru.diaproject.vkplus.news.model.items.Notes;

public class NewsNoteItem extends NewsEntityBase<Notes> {
    public NewsNoteItem() {
        super(FilterType.NOTE);
    }
}
