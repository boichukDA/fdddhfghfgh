package ru.diaproject.vkplus.database.model;


import android.graphics.Bitmap;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
@DatabaseTable(tableName = "NEWS_CONFIGURATION")
public class NewsConfiguration implements Serializable {
    public final static String CONFIG_ID_COLUMN = "ID";
    public final static String TAB_VALUES_COLUMN = "TAB_VALUES";
    public final static String IS_DEFAULT_COLUMN = "IS_DEFAULT";

    @DatabaseField(unique = true, generatedId = true, dataType = DataType.INTEGER, columnName = CONFIG_ID_COLUMN)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.BYTE, columnName = TAB_VALUES_COLUMN)
    private byte tabValues;

    @DatabaseField(canBeNull = false, dataType = DataType.BOOLEAN, columnName = IS_DEFAULT_COLUMN)
    private boolean isDefault;

    @DatabaseField(persisted = false)
    private transient Bitmap audioPlayBitmap;

    @DatabaseField(persisted = false)
    private transient Bitmap postLikeButton;

    @DatabaseField(persisted = false)
    private transient Bitmap postCommentButton;

    @DatabaseField(persisted = false)
    private transient Bitmap postShareBitmap;

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

    public boolean isSet(int pos) {
        return (tabValues & (1 << pos)) != 0;
    }

    public void set(int pos, boolean value) {
        byte mask = (byte) (1 << pos);
        tabValues = (byte) ((tabValues & ~mask) | (value ? mask : 0));
    }

    public void setTabValues(byte tabValues) {
        this.tabValues = tabValues;
    }

    public Bitmap getAudioPlayBitmap() {
        return audioPlayBitmap;
    }

    public void setAudioPlayBitmap(Bitmap audioPlayBitmap) {
        this.audioPlayBitmap = audioPlayBitmap;
    }

    public Bitmap getPostLikeButton() {
        return postLikeButton;
    }

    public void setPostLikeButton(Bitmap postLikeButton) {
        this.postLikeButton = postLikeButton;
    }

    public Bitmap getPostCommentButton() {
        return postCommentButton;
    }

    public void setPostCommentButton(Bitmap postCommentButton) {
        this.postCommentButton = postCommentButton;
    }

    public Bitmap getPostShareBitmap() {
        return postShareBitmap;
    }

    public void setPostShareBitmap(Bitmap postShareBitmap) {
        this.postShareBitmap = postShareBitmap;
    }
}
