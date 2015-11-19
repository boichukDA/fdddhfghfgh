package ru.diaproject.vkplus.vkcore.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ru.diaproject.vkplus.VKPlusApplication;

public class VKPersistenceWorker implements IPersistenceWorker {

    private SharedPreferences preferences;
    private String[] sharedAddr = new String[3];
    private Gson gson;
    public VKPersistenceWorker(){
        gson = new Gson();

        sharedAddr[1] = VKPersistenceWorker.this.getClass().getPackage().toString();
        sharedAddr[0] = ".vk_token";
        sharedAddr[2] = ".vk_account";

        preferences =   VKPlusApplication.getStaticContext().getSharedPreferences( sharedAddr[1], Context.MODE_PRIVATE);
    }

    public void saveObject(IPersistence obj){
        String strObject = gson.toJson(obj);
        preferences.edit().putString(obj.getPersistenceUniqueName(), strObject).commit();
    }
    public <T extends IPersistence>  T tryToRestoreObject(Class<T> typeObject){
        String name = typeObject.getCanonicalName();
        String json = preferences.getString(name, "");
        T result = "".equals(json)?null:gson.fromJson(json, typeObject);
        return result;
    }
}
