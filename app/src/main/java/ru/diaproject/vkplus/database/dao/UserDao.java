package ru.diaproject.vkplus.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.diaproject.vkplus.database.model.MainConfiguration;
import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.database.workers.MainConfigurationWorker;
import ru.diaproject.vkplus.database.workers.NewsConfigurationWorker;

public class UserDao extends BaseDaoImpl<User, Integer> {
    public UserDao(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public int create(User data) throws SQLException {
        return super.create(data);
    }

    public User getCurrent(){
        try {
            List<User> results = queryForEq(User.IS_CURRENT_COLUMN, true);

            if (results.size() > 0)
                return results.get(0);
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int createUser(User data, Boolean  httpsRequired) throws SQLException {
        User user = getCurrent();
        if (user != null) {
            user.setIsCurrent(httpsRequired);
            try {
                update(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        MainConfiguration configuration = MainConfigurationWorker.createDefault();
        configuration.setHttpsRequired(false);
        data.setMainConfiguration(configuration);

        NewsConfiguration newsConfiguration = NewsConfigurationWorker.createDefault();
        data.setNewsConfiguration(newsConfiguration);

        return create(data);
    }
}
