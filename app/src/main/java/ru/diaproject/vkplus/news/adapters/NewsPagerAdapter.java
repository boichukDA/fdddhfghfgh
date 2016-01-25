package ru.diaproject.vkplus.news.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

import ru.diaproject.vkplus.news.NewsActivity;
import ru.diaproject.vkplus.news.NewsFragmentSelector;
import ru.diaproject.vkplus.news.NewsUserConfig;
import ru.diaproject.vkplus.news.NewsVariantContainer;
import ru.diaproject.vkplus.news.fragments.OnFabStateChangeListener;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class NewsPagerAdapter extends FragmentPagerAdapter{
    private NewsActivity context;
    private LayoutInflater inflater;
    private VKUser user;
    private NewsVariantContainer container;
    private OnFabStateChangeListener listener;

    public NewsPagerAdapter(FragmentManager fm, NewsActivity context, VKUser user, OnFabStateChangeListener listener) {
        super(fm);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.user = user;
        this.container = NewsVariantContainer.getInstance(context, user.getConfiguration().getNewsUserConfiguration());
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return container.size();
    }

    @Override
    public Fragment getItem(int position) {
        return NewsFragmentSelector.getInstance(context, container.get(position).getId(), user, container.get(position), listener);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return container.get(position).getDescription();
    }
}