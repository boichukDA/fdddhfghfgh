package ru.diaproject.vkplus;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;

import ru.diaproject.vkplus.vkcore.VK;

public class VKPlusApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //registerComponentCallbacks(this);
        Glide.get(this).setMemoryCategory(MemoryCategory.LOW);
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
        Glide.get(this).onTrimMemory(level);
        Log.e("TRIM", "TRIM");
    }
}
