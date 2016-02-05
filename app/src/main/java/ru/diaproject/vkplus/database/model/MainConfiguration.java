package ru.diaproject.vkplus.database.model;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
@DatabaseTable(tableName = "MAIN_CONFIGURATION")
public class MainConfiguration implements Serializable {
    public final static String ID_COLUMN = "ID";
    public final static String HTTPS_REQUIRED_COLUMN = "HTTPS_REQUIRED";
    public final static String IS_DEFAULT_COLUMN = "IS_DEFAULT";

    @DatabaseField(unique = true, generatedId = true, dataType = DataType.INTEGER, columnName = ID_COLUMN)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = HTTPS_REQUIRED_COLUMN)
    private boolean httpsRequired;

    @DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = IS_DEFAULT_COLUMN)
    private boolean isDefault;

    public boolean isHttpsRequired() {
        return httpsRequired;
    }

    public void setHttpsRequired(boolean httpsRequired) {
        this.httpsRequired = httpsRequired;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
