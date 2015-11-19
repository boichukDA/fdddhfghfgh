package ru.diaproject.vkplus.core.utils;


import ru.diaproject.vkplus.R;


public enum ColorScheme {
    VK_CLASSIC(R.color.vk_main_color);

    private int backgroundColor;
    ColorScheme(int backgroundColor){
        this.backgroundColor = backgroundColor;

    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
