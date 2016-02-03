package ru.diaproject.vkplus.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;


import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;

public abstract class NewsFragmentSelector {
    private static final String ARG_USER = "user";
    private static final String ARG_VARIANT = "variant";

    public  static Fragment getInstance(User user, NewsVariant newsVariant){
        NewsPagerCardFragment f = new NewsPagerCardFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_USER, user);
        b.putParcelable(ARG_VARIANT, newsVariant);
        f.setArguments(b);
        return f;
    }
}
