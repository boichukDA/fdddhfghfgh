package ru.diaproject.vkplus.news;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;

public class NewsVariant implements Parcelable {
    public static final Parcelable.Creator<NewsVariant> CREATOR = new Parcelable.Creator<NewsVariant>() {
        public NewsVariant createFromParcel(Parcel in) {
            return new NewsVariant(in);
        }

        public NewsVariant[] newArray(int size) {
            return new NewsVariant[size];
        }
    };

    private Integer id;
    private Drawable icon;
    private String description;
    private boolean isSet;
    private String postFilter;
    private IDataFilter filter;

    public NewsVariant(Integer id, Drawable icon, String description, boolean isSet, String postFilter, VKQuerySubMethod subMethod, IDataFilter filter) {
        this.id = id;
        this.icon = icon;
        this.description = description;
        this.isSet = isSet;
        this.postFilter = postFilter;
        this.subMethod = subMethod;
        this.filter = filter;
    }

    public NewsVariant(Parcel in) {
        id = in.readInt();
        isSet = in.readByte() != 0;
        description = in.readString();
        postFilter = in.readString();
        subMethod = VKQuerySubMethod.valueOf(in.readString());
        filter = (IDataFilter) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (isSet ? 1 : 0));
        dest.writeString(description);
        dest.writeString(postFilter);
        dest.writeString(subMethod.getValue());
        if (filter!=null)
            try {
                dest.writeSerializable(filter);
            }
            catch(Throwable e){}
    }

    private VKQuerySubMethod subMethod;

    public String getPostFilter() {
        return postFilter;
    }

    public VKQuerySubMethod getSubMethod() {
        return subMethod;
    }

    public Integer getId() {
        return id;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setIsSet(boolean isSet) {
        this.isSet = isSet;
    }

    public IDataFilter getFilter() {
        return filter;
    }
}
