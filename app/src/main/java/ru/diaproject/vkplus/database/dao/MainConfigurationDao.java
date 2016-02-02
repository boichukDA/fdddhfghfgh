package ru.diaproject.vkplus.database.dao;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.diaproject.vkplus.database.model.MainConfiguration;

public class MainConfigurationDao extends BaseDaoImpl<MainConfiguration, Integer>{

    public MainConfigurationDao(ConnectionSource connectionSource, Class<MainConfiguration> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public MainConfiguration getDefault(){
        try {
            List<MainConfiguration> results = queryForEq(MainConfiguration.IS_DEFAULT_COLUMN, true);

            if (results.size() > 0)
                return results.get(0);
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
