package ru.diaproject.vkplus.model.users;

import ru.diaproject.vkplus.model.JsonResponseParser;
import ru.diaproject.vkplus.json.users.UserResponseJsonHandler;

@JsonResponseParser(jsonParser = UserResponseJsonHandler.class)
public interface IDataUser extends IDataOwner {
    String getFirstName();

    String getLastName();

    DeactivatedType getType();

    boolean isHidden();

    String getScreenName();

    NetworkStatus getNetworkStatus();

    String getStatus();
}
