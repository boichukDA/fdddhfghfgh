package ru.diaproject.vkplus.database.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.diaproject.vkplus.database.model.NewsConfiguration;

public class NewsConfigurationDao extends BaseDaoImpl<NewsConfiguration, Integer> {
    public NewsConfigurationDao(ConnectionSource connectionSource, Class<NewsConfiguration> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public NewsConfiguration getDefault(){
        try {
            List<NewsConfiguration> results = queryForEq(NewsConfiguration.IS_DEFAULT_COLUMN, true);

            if (results.size() > 0)
                return results.get(0);
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
