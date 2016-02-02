package ru.diaproject.vkplus.database.workers;


import java.sql.SQLException;

import ru.diaproject.vkplus.database.HelperFactory;
import ru.diaproject.vkplus.database.model.MainConfiguration;

public class MainConfigurationWorker {

    public static MainConfiguration getDefault(){
        try {
            return HelperFactory.getHelper().getMainConfigurationDao().getDefault();
        } catch (SQLException e) {
            return null;
        }
    }

    public static MainConfiguration createDefault(){
        MainConfiguration configuration = new MainConfiguration();
        configuration.setIsDefault(false);
        configuration.setHttpsRequired(true);
        create(configuration);
        return configuration;
    }

    public static int create(MainConfiguration config){
        try {
            return HelperFactory.getHelper().getMainConfigurationDao().create(config);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
