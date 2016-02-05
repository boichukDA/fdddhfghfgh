package ru.diaproject.vkplus.database.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "COLOR_SCHEME")
public class ColorScheme implements Serializable {
    public final static String ID_COLUMN = "ID";
    public final static String NAME_COLUMN = "NAME";

    public final static String STATUS_BAR_COLOR_COLUMN = "STATUS_BAR_COLOR";
    public final static String MAIN_COLOR_COLUMN = "MAIN_COLOR";
    public final static String TITLE_COLOR_COLUMN = "TITLE_COLOR";

    public final static String ACCENT_COLOR_COLUMN = "ACCENT_COLOR";
    public final static String TEXT_COLOR_COLUMN = "TEXT_COLOR";

    public final static String BACKGROUND_COLOR_COLUMN = "BACKGROUND_COLOR";
    public final static String CARD_COLOR_COLUMN = "CARD_COLOR";

    @DatabaseField(unique = true, generatedId = true, dataType = DataType.INTEGER, columnName = ID_COLUMN)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = NAME_COLUMN)
    private String name;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = STATUS_BAR_COLOR_COLUMN)
    private int statusBarColor;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = MAIN_COLOR_COLUMN)
    private int mainColor;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = TITLE_COLOR_COLUMN)
    private int titleColor;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = ACCENT_COLOR_COLUMN)
    private int accentColor;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = TEXT_COLOR_COLUMN)
    private int textColor;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = BACKGROUND_COLOR_COLUMN)
    private int backgroundColor;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = CARD_COLOR_COLUMN)
    private int cardColor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(Integer statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public Integer getMainColor() {
        return mainColor;
    }

    public void setMainColor(Integer mainColor) {
        this.mainColor = mainColor;
    }

    public Integer getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(Integer titleColor) {
        this.titleColor = titleColor;
    }

    public Integer getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(Integer accentColor) {
        this.accentColor = accentColor;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public void setTextColor(Integer textColor) {
        this.textColor = textColor;
    }

    public Integer getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Integer backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getCardColor() {
        return cardColor;
    }

    public void setCardColor(Integer cardColor) {
        this.cardColor = cardColor;
    }
}
