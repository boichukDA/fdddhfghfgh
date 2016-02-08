package ru.diaproject.vkplus.model;

import java.io.Serializable;

import ru.diaproject.vkplus.model.users.IDataObject;

public abstract class DataObject implements IDataObject,Serializable {
    private Integer id;

    public DataObject(){

    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return ((DataObject)o).id.equals(id);
    }
}
