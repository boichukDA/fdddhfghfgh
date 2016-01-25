package ru.diaproject.vkplus.news.model.groups;

import ru.diaproject.vkplus.news.model.users.DeactivatedType;
import ru.diaproject.vkplus.news.model.users.IDataOwner;

public interface IDataGroup extends IDataOwner {
    String getName();

    String getScreenName();

    CloseType getIsClosed() ;

    DeactivatedType getDeactivatedType();

    Boolean getIsAdmin() ;

    AdminLevelType getAdminLevel();

    Boolean getIsMember();

    GroupType getGroupType();

    String getPhoto200() ;

}
