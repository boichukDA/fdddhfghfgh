package ru.diaproject.vkplus.news.model.users;


import ru.diaproject.vkplus.news.model.DataObject;
import ru.diaproject.vkplus.news.model.IDataResult;

public abstract class DataOwner extends DataObject implements IDataOwner, IDataResult {
    private String fullName;
    private String photo100;

    public DataOwner() {
    }

    @Override
    public final String getFullName() {
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }

}
