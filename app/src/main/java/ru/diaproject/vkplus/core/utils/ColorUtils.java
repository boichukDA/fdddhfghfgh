package ru.diaproject.vkplus.core.utils;

import android.graphics.Color;

public class ColorUtils {
    public  static final byte OPACITY_100 = (byte) 0xFF;
    public static final byte OPACITY_55 = (byte) 0x8C;

    public static int setColorAlpha(int color, byte alpha) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }
}
