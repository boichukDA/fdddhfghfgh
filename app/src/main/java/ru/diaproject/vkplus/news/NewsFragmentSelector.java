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
    private static HashMap<Integer, Fragment> newsFragments = new HashMap<>();

    public  static Fragment getInstance(int id, VKUser user, NewsVariant newsVariant, OnFabStateChangeListener listener){
        if (newsFragments.containsKey(id))
            return newsFragments.get(id);

        NewsPagerCardFragment f = new NewsPagerCardFragment();
        Bundle b = new Bundle();
        b.putParcelable(ARG_USER, user);
        b.putParcelable(ARG_VARIANT, newsVariant);
        f.setArguments(b);
        f.setOnFabStateChangeListener(listener);
        newsFragments.put(id, f);
        return f;
    }
}