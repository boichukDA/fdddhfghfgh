package ru.diaproject.vkplus.news.model.users;

import ru.diaproject.vkplus.news.model.JsonResponseParser;
import ru.diaproject.vkplus.news.model.json.users.UserResponseJsonHandler;

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
