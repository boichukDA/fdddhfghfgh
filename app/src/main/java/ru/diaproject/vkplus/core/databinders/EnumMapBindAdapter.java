package ru.diaproject.vkplus.core.databinders;

import java.util.HashMap;
import java.util.Map;

public abstract class EnumMapBindAdapter<E extends Enum<E>> extends DataBindAdapter {

    private Map<E, DataBinder> mBinderMap = new HashMap<>();

    @Override
    public abstract  int getItemCount();

    @Override
    public abstract int getItemViewType(int position);

    public abstract <T extends DataBinder> T getDataBinder(int viewType) ;


    public <T extends DataBinder> T getDataBinder(E e) {
        return (T) mBinderMap.get(e);
    }

    public Map<E, DataBinder> getBinderMap() {
        return mBinderMap;
    }

    public void putBinder(E e, DataBinder binder) {
        mBinderMap.put(e, binder);
    }
}