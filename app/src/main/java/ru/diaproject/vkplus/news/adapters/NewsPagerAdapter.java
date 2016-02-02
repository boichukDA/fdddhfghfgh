package ru.diaproject.vkplus.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.news.NewsActivity;
import ru.diaproject.vkplus.news.NewsFragmentSelector;
import ru.diaproject.vkplus.news.NewsVariantContainer;
import ru.diaproject.vkplus.news.fragments.OnFabStateChangeListener;

public class NewsPagerAdapter extends FragmentPagerAdapter{
    private NewsActivity context;
    private LayoutInflater inflater;
    private User user;
    private NewsVariantContainer container;
    private OnFabStateChangeListener listener;

    public NewsPagerAdapter(FragmentManager fm, NewsActivity context, User user, OnFabStateChangeListener listener) {
        super(fm);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.user = user;
        this.container = NewsVariantContainer.getInstance(context, user);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return container.size();
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragmentSelector.getInstance(user, container.get(position), listener);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return container.get(position).getDescription();
    }
}