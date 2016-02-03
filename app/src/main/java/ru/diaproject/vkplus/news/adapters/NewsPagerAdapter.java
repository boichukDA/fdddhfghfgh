package ru.diaproject.vkplus.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.news.NewsActivity;
import ru.diaproject.vkplus.news.NewsFragmentSelector;
import ru.diaproject.vkplus.news.NewsVariantContainer;

public class NewsPagerAdapter extends FragmentPagerAdapter{
    private NewsActivity context;
    private LayoutInflater inflater;
    private User user;
    private NewsVariantContainer container;

    public NewsPagerAdapter(FragmentManager fm, NewsActivity context, User user) {
        super(fm);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.user = user;
        this.container = NewsVariantContainer.getInstance(context, user);
    }

    @Override
    public int getCount() {
        return container.size();
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragmentSelector.getInstance(user, container.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return container.get(position).getDescription();
    }
}