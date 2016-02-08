package ru.diaproject.vkplus.model.groups;

import ru.diaproject.vkplus.model.users.DeactivatedType;
import ru.diaproject.vkplus.model.users.IDataOwner;

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
