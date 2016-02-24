package ru.diaproject.vkplus.vkcore.queries.customs;

import ru.diaproject.vkplus.database.model.User;

public class ResponseManager {
    private User user;

    public ResponseManager(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
