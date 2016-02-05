package ru.diaproject.vkplus.database.dao;



import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ru.diaproject.vkplus.database.model.ColorScheme;


public class ColorSchemeDao extends BaseDaoImpl<ColorScheme, Integer> {
    public static final int COLOR_SCHEME_LIGHT = 1;

    public ColorSchemeDao(ConnectionSource connectionSource, Class<ColorScheme> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ColorScheme getDefault() {
        try {
            List<ColorScheme> results = queryForEq(ColorScheme.ID_COLUMN, COLOR_SCHEME_LIGHT);
            if (results.size() > 0)
                return results.get(0);
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
