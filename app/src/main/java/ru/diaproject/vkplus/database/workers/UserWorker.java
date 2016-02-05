package ru.diaproject.vkplus.database.workers;

import java.sql.SQLException;

import ru.diaproject.vkplus.database.HelperFactory;
import ru.diaproject.vkplus.database.model.User;

public class UserWorker {
    public static User getCurrentUser(){
        try {
            User user =  HelperFactory.getHelper().getUserDao().getCurrent();
            if (user != null) {
                HelperFactory.getHelper().getNewsConfigurationDao().refresh(user.getNewsConfiguration());
                HelperFactory.getHelper().getMainConfigurationDao().refresh(user.getMainConfiguration());
                HelperFactory.getHelper().getColorSchemeDao().refresh(user.getColorScheme());
            }
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public static int createUser(User user, Boolean  httpsRequired){
        try {
            return HelperFactory.getHelper().getUserDao().createUser(user, httpsRequired);
        } catch (SQLException e) {
            return -1;
        }
    }
}
