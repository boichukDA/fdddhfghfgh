package ru.diaproject.ptrrecyclerview;

import rx.Observable;

public interface PagingListener<T> {
    Observable<T> onNextPage(int offset);
}

