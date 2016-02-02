package ru.diaproject.vkplus.photoviewer.model;


import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import ru.diaproject.vkplus.core.utils.WindowUtils;

public class ImageData implements Parcelable {
    public static final Creator<ImageData> CREATOR = new Creator<ImageData>() {
        @Override
        public ImageData createFromParcel(Parcel in) {
            return new ImageData(in);
        }

        @Override
        public ImageData[] newArray(int size) {
            return new ImageData[size];
        }
    };

    public static ImageData fromImageView(ImageView view, Context context){
        int [] positions = new int[2];
        view.getLocationOnScreen(positions);

        Rect r = new Rect();
        view.getLocalVisibleRect(r);

        ImageData data = new ImageData();
        data.setX(positions[0]);
        data.setY(positions[1]- WindowUtils.getStatusBarHeight(context));
        data.setWidth(view.getWidth());
        data.setHeight(view.getHeight());
        data.setVisibleHeightStart(r.top);
        data.setVisibleHeightEnd(r.bottom);
        return data;
    }

    public ImageData(){

    }

    protected ImageData(Parcel in) {
        x = in.readInt();
        y = in.readInt();
        width = in.readInt();
        height = in.readInt();
        visibleHeightStart = in.readInt();
        visibleHeightEnd = in.readInt();
    }

    private int x;
    private int y;
    private int width;
    private int height;
    private int visibleHeightStart;
    private int visibleHeightEnd;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVisibleHeightStart() {
        return visibleHeightStart;
    }

    public void setVisibleHeightStart(int visibleHeightStart) {
        this.visibleHeightStart = visibleHeightStart;
    }

    public int getVisibleHeightEnd() {
        return visibleHeightEnd;
    }

    public void setVisibleHeightEnd(int visibleHeightEnd) {
        this.visibleHeightEnd = visibleHeightEnd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(x);
        dest.writeInt(y);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(visibleHeightStart);
        dest.writeInt(visibleHeightEnd);
    }
}
