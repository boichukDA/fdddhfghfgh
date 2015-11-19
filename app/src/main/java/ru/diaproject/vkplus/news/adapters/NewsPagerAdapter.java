package ru.diaproject.vkplus.news.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import ru.diaproject.vkplus.news.NewsFragmentSelector;
import ru.diaproject.vkplus.news.NewsVariantContainer;
import ru.diaproject.vkplus.news.fragments.OnFabStateChangeListener;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class NewsPagerAdapter extends FragmentPagerAdapter{
    private Context context;
    private LayoutInflater inflater;
    private VKUser user;
    private NewsVariantContainer container;
    private OnFabStateChangeListener listener;

    public NewsPagerAdapter(FragmentManager fm, Context context, VKUser user, OnFabStateChangeListener listener) {
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
        return NewsFragmentSelector.getInstance(container.get(position).getId(), user, container.get(position), listener);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return container.get(position).getDescription();
    }
}