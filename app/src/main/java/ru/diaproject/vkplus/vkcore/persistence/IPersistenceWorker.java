package ru.diaproject.vkplus.vkcore.persistence;

public interface IPersistenceWorker {
     void saveObject(IPersistence obj);
     <T extends IPersistence>  T tryToRestoreObject(Class<T> typeObject);
}
