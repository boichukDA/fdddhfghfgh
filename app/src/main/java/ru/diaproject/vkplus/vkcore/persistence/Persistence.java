package ru.diaproject.vkplus.vkcore.persistence;


public abstract class Persistence implements IPersistence{
    private static int counter = 0;
    private static int getAndIncrement(){
        return counter++;
    }

    public String persistenceName;

    public Persistence(){
        persistenceName = this.getClass().getCanonicalName()+getAndIncrement();
    }
    public  String getPersistenceUniqueName(){
        return persistenceName;
    }

    public  void setPersistenceUniqueName(String persistenceName){
        this.persistenceName = persistenceName;
    }
}

