package ru.diaproject.vkplus.news;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;

import ru.diaproject.vkplus.news.adapters.NewsPagerAdapter;
import ru.diaproject.vkplus.news.fragments.OnFabStateChangeListener;
import ru.diaproject.vkplus.vkcore.user.VKUser;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class NewsActivity extends ParentActivityNoTitle {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager pager;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void initBackend(Bundle savedInstanceState) {
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.news_layout);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(this, R.color.m_indigo700));
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.news));
        }

        toolbar.setCollapsible(true);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_default_material_dark));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.coordinator), "I'm a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NewsActivity.this, "Snackbar Action", Toast.LENGTH_LONG).show();
                    }
                }).show();
            }
        });
        PagerAdapter myAdapter = new NewsPagerAdapter(getSupportFragmentManager(), this,  getUser(), new OnFabStateChangeListener(){
            @Override
            public void onShow() {
                showFab();
            }

            @Override
            public void onHide() {
hideFab();
            }
        });
        pager.setAdapter(myAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        pager.setCurrentItem(0);
    }

    private void hideFab() {
        Log.e("FAB", "HIDE");
        fab.setVisibility(View.GONE);
        fab.hide();
    }

    private void showFab() {
        Log.e("FAB", "SHOW");
        fab.setVisibility(View.VISIBLE);
        fab.show();
    }

    @Override
    protected String getLogName() {
        return null;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
