package ru.diaproject.vkplus.news.model.users;


public interface IDataOwner extends IDataObject{
    String getFullName();
    OwnerSex getSex();
    Integer getPlaceholderResource();
    Integer getErrorResource();
    String getPhoto100();
}
