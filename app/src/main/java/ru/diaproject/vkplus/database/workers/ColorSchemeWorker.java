package ru.diaproject.vkplus.database.workers;


import java.sql.SQLException;

import ru.diaproject.vkplus.database.HelperFactory;
import ru.diaproject.vkplus.database.model.ColorScheme;

public class ColorSchemeWorker {
    public static ColorScheme getDefault(){
        try {
            return HelperFactory.getHelper().getColorSchemeDao().getDefault();
        } catch (SQLException e) {
            return null;
        }
    }
}
