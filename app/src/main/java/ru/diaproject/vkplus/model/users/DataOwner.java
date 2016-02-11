package ru.diaproject.vkplus.model.users;


import android.text.Spannable;

import ru.diaproject.vkplus.model.DataObject;
import ru.diaproject.vkplus.model.IDataResult;


public abstract class DataOwner extends DataObject implements IDataOwner, IDataResult {
    private transient Spannable fullName;
    private String photo100;

    public DataOwner() {
    }

    @Override
    public final Spannable getFullName() {
            return fullName;
    }

    @Override
    public abstract OwnerSex getSex();

    @Override
    public abstract Integer getPlaceholderResource();

    @Override
    public abstract Integer getErrorResource() ;

    @Override
    public final String getPhoto100(){
        return photo100;
    }

    public void setFullName(Spannable fullName) {
        this.fullName = fullName;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }

}
