package ru.diaproject.vkplus.profiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.database.model.ColorScheme;

public class VKProfileDetailsActivity extends ParentActivityNoTitle{
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    @Override
    protected void initContent(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    protected void initBackend(Bundle savedInstanceState) {

    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.profile_main_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Second Activity");
    }

    @Override
    protected void initColorScheme(ColorScheme colorScheme) {
        super.initColorScheme(colorScheme);
        toolbar.setTitleTextColor(colorScheme.getTitleColor());
        colorizeToolbar(toolbar, colorScheme.getTitleColor());

        appBarLayout.setBackgroundColor(colorScheme.getMainColor());
        collapsingToolbar.setBackgroundColor(colorScheme.getMainColor());
        collapsingToolbar.setContentScrimColor(colorScheme.getMainColor());
        collapsingToolbar.setStatusBarScrimColor(colorScheme.getMainColor());
    }

    @Override
    protected String getLogName() {
        return getClass().getSimpleName();
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }
}
