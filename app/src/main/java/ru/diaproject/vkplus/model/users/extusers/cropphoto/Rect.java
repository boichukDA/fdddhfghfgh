package ru.diaproject.vkplus.model.users.extusers.cropphoto;


import org.json.JSONObject;

public class Rect {
    public static final String JSON_X = "x";
    public static final String JSON_Y = "y";
    public static final String JSON_X2 = "x2";
    public static final String JSON_Y2 = "y2";

    public static Rect parseObject(JSONObject object){
        Rect rect = new Rect();
        rect.setX(object.optDouble(JSON_X));
        rect.setY(object.optDouble(JSON_Y));
        rect.setY2(object.optDouble(JSON_Y2));
        rect.setX2(object.optDouble(JSON_X2));
        return rect;
    }

    private double x;
    private double y;
    private double x2;
    private double y2;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
