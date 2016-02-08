package ru.diaproject.vkplus.core.databinders;

import android.content.Context;
import android.view.ViewGroup;

import ru.diaproject.vkplus.news.binders.bindhelpers.HeaderBindHelper;
import ru.diaproject.vkplus.model.NewsResponse;
import ru.diaproject.vkplus.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.news.viewholders.base.DataMainViewHolder;

abstract public class DataBinder<T extends DataMainViewHolder, V extends IDataMainItem> {
    private NewsResponse items;
    private Context context;
    private DataBindAdapter mDataBindAdapter;
    private HeaderBindHelper headerBindHelper;

    public DataBinder(Context context, DataBindAdapter dataBindAdapter, NewsResponse items) {
        this.context = context;
        mDataBindAdapter = dataBindAdapter;
        this.items = items;
        this.headerBindHelper = new HeaderBindHelper(context);
    }

    abstract public T newViewHolder(ViewGroup parent);

    abstract public void bindViewHolder(T holder, int position);

    public void setDataOwner(T holder, V currentEntity){
        IDataOwner owner = currentEntity.getItemOwner();
        headerBindHelper.setHeader(owner.getFullName(), owner.getPhoto100(), currentEntity.getDate(),
                holder);
    }
    public V getItem(int position){
        return (V) items.getListItems().get(position);
    }

    public Context getContext(){
        return context;
    }
}