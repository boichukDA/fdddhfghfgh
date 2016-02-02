package ru.diaproject.vkplus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.diaproject.vkplus.database.dao.MainConfigurationDao;
import ru.diaproject.vkplus.database.dao.NewsConfigurationDao;
import ru.diaproject.vkplus.database.dao.UserDao;
import ru.diaproject.vkplus.database.model.MainConfiguration;
import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.database.model.User;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DataBaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="vkdatabase.db";

    private static final int DATABASE_VERSION = 1;

    private UserDao userDao;
    private MainConfigurationDao mainConfigurationDao;
    private NewsConfigurationDao newsConfigurationDao;

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public UserDao getUserDao() throws SQLException{
        if(userDao == null){
            userDao = new UserDao(getConnectionSource(), User.class);
        }
        return userDao;
    }

    public MainConfigurationDao getMainConfigurationDao() throws SQLException{
        if(mainConfigurationDao == null){
            mainConfigurationDao = new MainConfigurationDao(getConnectionSource(), MainConfiguration.class);
        }
        return mainConfigurationDao;
    }

    public NewsConfigurationDao getNewsConfigurationDao() throws SQLException{
        if(newsConfigurationDao == null){
            newsConfigurationDao = new NewsConfigurationDao(getConnectionSource(), NewsConfiguration.class);
        }
        return newsConfigurationDao;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, MainConfiguration.class);
            TableUtils.createTable(connectionSource, NewsConfiguration.class);

            if (getMainConfigurationDao().getDefault() == null) {
                MainConfiguration defaultConfiguration = new MainConfiguration();
                defaultConfiguration.setIsDefault(true);
                defaultConfiguration.setHttpsRequired(true);

                getMainConfigurationDao().create(defaultConfiguration);
            }

            if (getNewsConfigurationDao().getDefault() == null) {
                NewsConfiguration defaultNewsConfiguration = new NewsConfiguration();
                defaultNewsConfiguration.setIsDefault(true);
                defaultNewsConfiguration.setTabValues((byte) 255);

                getNewsConfigurationDao().create(defaultNewsConfiguration);
            }

        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, MainConfiguration.class, true);
            TableUtils.dropTable(connectionSource, NewsConfiguration.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(){
        super.close();
        mainConfigurationDao = null;
        userDao = null;
    }
}
