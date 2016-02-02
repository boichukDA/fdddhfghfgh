package ru.diaproject.vkplus;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import ru.diaproject.vkplus.database.HelperFactory;


public class VKPlusApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        HelperFactory.setHelper(getApplicationContext());
        HelperFactory.getHelper().getWritableDatabase();
    }

    public static Context getStaticContext(){
        return context;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e("TRIM", "TRIM");
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
