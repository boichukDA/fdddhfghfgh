package ru.diaproject.vkplus.database.workers;

import java.sql.SQLException;

import ru.diaproject.vkplus.database.HelperFactory;
import ru.diaproject.vkplus.database.model.NewsConfiguration;

public class NewsConfigurationWorker {
    public static NewsConfiguration getDefault(){
        try {
            return HelperFactory.getHelper().getNewsConfigurationDao().getDefault();
        } catch (SQLException e) {
            return null;
        }
    }

    public static NewsConfiguration createDefault(){
        NewsConfiguration configuration = new NewsConfiguration();
        configuration.setIsDefault(false);
        configuration.setTabValues((byte) 255);
        create(configuration);
        return configuration;
    }

    public static int create(NewsConfiguration config){
        try {
            return HelperFactory.getHelper().getNewsConfigurationDao().create(config);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
