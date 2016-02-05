package ru.diaproject.vkplus.database;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.VKPlusApplication;
import ru.diaproject.vkplus.database.dao.ColorSchemeDao;
import ru.diaproject.vkplus.database.dao.MainConfigurationDao;
import ru.diaproject.vkplus.database.dao.NewsConfigurationDao;
import ru.diaproject.vkplus.database.dao.UserDao;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.database.model.MainConfiguration;
import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.database.model.User;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DataBaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="vkdatabase.db";

    private static final int DATABASE_VERSION = 1;

    private Context context;

    private UserDao userDao;
    private MainConfigurationDao mainConfigurationDao;
    private NewsConfigurationDao newsConfigurationDao;
    private ColorSchemeDao colorSchemeDao;

    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context = VKPlusApplication.getStaticContext();
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

    public ColorSchemeDao getColorSchemeDao() throws SQLException{
        if(colorSchemeDao == null){
            colorSchemeDao = new ColorSchemeDao(getConnectionSource(), ColorScheme.class);
        }
        return colorSchemeDao;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, MainConfiguration.class);
            TableUtils.createTable(connectionSource, NewsConfiguration.class);
            TableUtils.createTable(connectionSource, ColorScheme.class);

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

            if (getColorSchemeDao().getDefault() == null) {
                ColorScheme colorScheme = new ColorScheme();
                colorScheme.setId(ColorSchemeDao.COLOR_SCHEME_LIGHT);
                colorScheme.setName("Default light");
                colorScheme.setStatusBarColor(ContextCompat.getColor(context, R.color.md_teal_700));
                colorScheme.setMainColor(ContextCompat.getColor(context, R.color.md_teal_500));

                colorScheme.setTitleColor(ContextCompat.getColor(context, R.color.md_black_1000));
                colorScheme.setAccentColor(ContextCompat.getColor(context, R.color.md_pink_A200));

                colorScheme.setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_850));
                colorScheme.setCardColor(ContextCompat.getColor(context, R.color.md_grey_800));
                colorScheme.setTextColor(ContextCompat.getColor(context, R.color.md_deep_orange_500));

                getColorSchemeDao().create(colorScheme);
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
            TableUtils.dropTable(connectionSource, ColorScheme.class, true);
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
