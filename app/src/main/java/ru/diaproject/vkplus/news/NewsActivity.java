package ru.diaproject.vkplus.news;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;


import java.util.ArrayDeque;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;

import ru.diaproject.vkplus.core.ParentFragment;
import ru.diaproject.vkplus.core.utils.BitmapUtils;
import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.news.adapters.NewsPagerAdapter;
import ru.diaproject.vkplus.news.fragments.OnFabStateChangeListener;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class NewsActivity extends ParentActivityNoTitle {
    public static final String STACK_NAME = "NewsActivityStack";

    private ArrayDeque<ParentFragment> fragments;
    TabLayout tabLayout;
    ViewPager pager;
    FloatingActionButton fab;
    Toolbar toolbar;
    FrameLayout fragmentsLayout;

    @Override
    protected void initBackend(Bundle savedInstanceState) {
        fragments = new ArrayDeque<>();
        initUserConfiguration(getUser());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PagerAdapter myAdapter = new NewsPagerAdapter(NewsActivity.this.getSupportFragmentManager(),
                        NewsActivity.this, getUser(), new OnFabStateChangeListener() {
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
        });
    }

    private void initUserConfiguration(User user) {
        NewsConfiguration configuration = user.getNewsConfiguration();

        Bitmap audioPlay = BitmapUtils.appyColorFilterForResource(this, R.drawable.news_post_audio_play_large_white, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);
        configuration.setAudioPlayBitmap(audioPlay);

        Bitmap likeBitmap = BitmapUtils.appyColorFilterForResource(this, R.drawable.news_post_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);
        configuration.setPostLikeButton(likeBitmap);

        Bitmap commentBitmap = BitmapUtils.appyColorFilterForResource(this, R.drawable.news_post_comment, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);
        configuration.setPostCommentButton(commentBitmap);

        Bitmap shareBitmap = BitmapUtils.appyColorFilterForResource(this, R.drawable.news_share_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);
        configuration.setPostShareBitmap(shareBitmap);
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.news_layout);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        pager = (ViewPager) findViewById(R.id.viewpager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fragmentsLayout = (FrameLayout) findViewById(R.id.news_frontend_placeholder);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.news));
        }

        toolbar.setCollapsible(true);

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

    public void startFragment(ParentFragment fragment){
        fragments.add(fragment);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.add(R.id.news_frontend_placeholder, fragment);
            trans.addToBackStack(null);
            trans.commit();
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    public void startFragment(ParentFragment fragment, String tag){
        fragments.add(fragment);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment searchFragment =  fragmentManager.findFragmentByTag(tag);
        if (searchFragment == null)
            System.out.print("");
        else System.out.print("");

        try {
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.replace(R.id.news_frontend_placeholder, fragment, tag);
            trans.commit();
        }catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (fragments.isEmpty())
            super.onBackPressed();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        try {
            ParentFragment fragment = fragments.pop();
            trans.remove(fragment);
            trans.commit();
        }
        catch(Throwable e){
            e.printStackTrace();
        }
    }
}
