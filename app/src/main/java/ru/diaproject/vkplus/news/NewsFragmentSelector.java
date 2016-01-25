package ru.diaproject.vkplus.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.HashMap;

import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.fragments.OnFabStateChangeListener;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public abstract class NewsFragmentSelector {
    private static final String ARG_USER = "user";
    private static final String ARG_VARIANT = "variant";

    public  static Fragment getInstance(NewsActivity context, int id, VKUser user, NewsVariant newsVariant, OnFabStateChangeListener listener){
        NewsPagerCardFragment f = new NewsPagerCardFragment();
        Bundle b = new Bundle();
        b.putParcelable(ARG_USER, user);
        b.putParcelable(ARG_VARIANT, newsVariant);
        f.setArguments(b);
        f.setOnFabStateChangeListener(listener);
        return f;
    }
}
