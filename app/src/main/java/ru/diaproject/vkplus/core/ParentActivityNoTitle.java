package ru.diaproject.vkplus.core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.SimpleTaskListener;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.BitmapUtils;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.database.model.User;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.customs.VKApi;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;

public abstract class ParentActivityNoTitle extends AppCompatActivity {
    public static final String VK_USER_ARG = "user";
    private User user;
    private DrawerImplementation impl;
    private Drawer drawer;

    private ColorScheme colorScheme;
    private void initContext(Bundle savedInstanceState){
         Intent intent = getIntent();
         Bundle bundle = intent.getExtras();
         User user = (User) bundle.getSerializable(VK_USER_ARG);
         if (user == null)
             throw new RuntimeException("User not come");

         setUser(user);
         colorScheme = user.getColorScheme();
         initContent(savedInstanceState, intent);
     }

    protected  void initContent(Bundle savedInstanceState, Intent activityIntent){}

    protected abstract void initBackend(Bundle savedInstanceState);

    protected abstract void initUI(Bundle savedInstanceState);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initContext(savedInstanceState);
            initUI(savedInstanceState);
            initColorScheme(colorScheme);
            VKMainExecutor.executeVKQuery(createQuery(),
                    new SimpleTaskListener<IDataUser>() {
                        @Override
                        public void onDone(final IDataUser result) {
                            impl = new DrawerImplementation();

                            final Bitmap icon = BitmapUtils.loadBitmap(result.getPhoto100(), ParentActivityNoTitle.this);
                            impl.setFriendsSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.drawer_friends_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            impl.setPhotoSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.news_photo_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            impl.setVideoSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.news_video_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            impl.setAudioSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.news_drawer_audios_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            impl.setMessageSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.drawer_charts_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            impl.setGroupSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.news_group_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            impl.setNewsSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.news_group_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            impl.setSettingsSelectedBitmap(BitmapUtils.appyColorFilter(ParentActivityNoTitle.this,
                                    R.drawable.drawer_settings_white, colorScheme.getTextColor(), PorterDuff.Mode.MULTIPLY));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initDrawer(result, icon);
                                }
                            });
                        }

                    } );

            VKMainExecutor.executeRunnable(new Runnable() {
                @Override
                public void run() {
                    try {
                        initBackend(savedInstanceState);
                    } catch (final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        } catch (final Throwable e) {
            Log.e(getLogName(), "exception", e);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    protected  void initColorScheme(ColorScheme colorScheme){

    }

    private void initDrawer(IDataUser result, Bitmap icon) {
        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName(result.getFirstName() + " " + result.getLastName());
        profileDrawerItem.withEmail(result.getStatus());
        profileDrawerItem.withEnabled(false);
        profileDrawerItem.withTextColor(colorScheme.getTitleColor());

        if (icon!= null)
            profileDrawerItem.withIcon(icon);
        else profileDrawerItem.withIcon(ContextCompat.getDrawable(this, R.drawable.man_siluette));

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(new ColorDrawable(colorScheme.getMainColor()))
                .withTextColor(colorScheme.getTitleColor())
                .addProfiles(profileDrawerItem)
                .withSelectionListEnabledForSingleProfile(false)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;//on profile icon click
                    }
                })
                .build();


        PrimaryDrawerItem myFriendsItem = new PrimaryDrawerItem();
        myFriendsItem.withIcon(new BitmapDrawable(getResources(), impl.getFriendsSelectedBitmap()));
        myFriendsItem.withTextColor(colorScheme.getTextColor());
        myFriendsItem.withName(R.string.drawer_item_my_friends)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(1);

        PrimaryDrawerItem myPhotoItem = new PrimaryDrawerItem();
        myPhotoItem.withIcon(new BitmapDrawable(getResources(), impl.getPhotoSelectedBitmap()));
        myPhotoItem.withTextColor(colorScheme.getTextColor());
        myPhotoItem.withName(R.string.drawer_item_my_photo)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(2);

        PrimaryDrawerItem myVideoItem = new PrimaryDrawerItem();
        myVideoItem.withIcon(new BitmapDrawable(getResources(), impl.getVideoSelectedBitmap()));
        myVideoItem.withTextColor(colorScheme.getTextColor());
        myVideoItem.withName(R.string.drawer_item_my_video)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(3);

        PrimaryDrawerItem myAudioItem = new PrimaryDrawerItem();
        myAudioItem.withIcon(new BitmapDrawable(getResources(), impl.getAudioSelectedBitmap()));
        myAudioItem.withTextColor(colorScheme.getTextColor());
        myAudioItem.withName(R.string.drawer_item_my_audio)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(4);

        PrimaryDrawerItem myMessageItem = new PrimaryDrawerItem();
        myMessageItem.withIcon(new BitmapDrawable(getResources(), impl.getMessageSelectedBitmap()));
        myMessageItem.withTextColor(colorScheme.getTextColor());
        myMessageItem.withName(R.string.drawer_item_my_messages)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(5);

        PrimaryDrawerItem myGroupItem = new PrimaryDrawerItem();
        myGroupItem.withIcon(new BitmapDrawable(getResources(), impl.getGroupSelectedBitmap()));
        myGroupItem.withTextColor(colorScheme.getTextColor());
        myGroupItem.withName(R.string.drawer_item_my_group)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(6);

        PrimaryDrawerItem myNewsItem = new PrimaryDrawerItem();
        myNewsItem.withIcon(new BitmapDrawable(getResources(), impl.getNewsSelectedBitmap()));
        myNewsItem.withTextColor(colorScheme.getTextColor());
        myNewsItem.withName(R.string.drawer_item_my_news)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(7);

        PrimaryDrawerItem mySettingsItem = new PrimaryDrawerItem();
        mySettingsItem.withIcon(new BitmapDrawable(getResources(), impl.getSettingsSelectedBitmap()));
        mySettingsItem.withTextColor(colorScheme.getTextColor());
        mySettingsItem.withName(R.string.drawer_item_settings)
                .withSelectedColor(colorScheme.getBackgroundColor())
                .withSelectedTextColor(colorScheme.getTextColor())
                .withIdentifier(8);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(getToolbar())
                .withActionBarDrawerToggle(false)
                .withStatusBarColor(colorScheme.getStatusBarColor())
                .withSliderBackgroundColor(colorScheme.getCardColor())
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        myFriendsItem,
                        myPhotoItem,
                        myVideoItem,
                        myAudioItem,
                        myMessageItem,
                        myGroupItem,
                        myNewsItem,
                        mySettingsItem
                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        return false;// jn item click

                    }
                })
                .build();
        setDrawerItemSelected(drawer);
    }

    public void setDrawerItemSelected(Drawer drawer) {

    }

    private VKQuery<IDataUser> createQuery(){
        return VKApi.users(user).get()
                .with(VKParameter.FIELDS, "city,verified,status,photo_100")
                .and(VKParameter.USER_IDS, user.getAccountId()).build();
    }

    protected  String getLogName(){
        return getClass().getSimpleName();
    }
    protected abstract Toolbar getToolbar();

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public void startActivity(Intent intent) {
        intent.putExtra(VK_USER_ARG, getUser());
        super.startActivity(intent);
    }

    public Drawer getDrawer() {
        return drawer;
    }

    @Override
    public void onBackPressed() {
        if (drawer!= null && drawer.isDrawerOpen())
            drawer.closeDrawer();
        else
            super.onBackPressed();
    }

    public ColorScheme getColorScheme() {
        return colorScheme;
    }

    public static void colorizeToolbar(Toolbar toolbarView, int toolbarIconsColor) {
        final PorterDuffColorFilter colorFilter
                = new PorterDuffColorFilter(toolbarIconsColor, PorterDuff.Mode.MULTIPLY);

        for (int i = 0; i < toolbarView.getChildCount(); i++) {
            final View v = toolbarView.getChildAt(i);

            if (v instanceof ImageButton) {
                ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
            }

            if (v instanceof ActionMenuView) {
                for (int j = 0; j < ((ActionMenuView) v).getChildCount(); j++) {

                    final View innerView = ((ActionMenuView) v).getChildAt(j);

                    if (innerView instanceof ActionMenuItemView) {
                        int drawablesCount = ((ActionMenuItemView) innerView).getCompoundDrawables().length;
                        for (int k = 0; k < drawablesCount; k++) {
                            if (((ActionMenuItemView) innerView).getCompoundDrawables()[k] != null) {
                                final int finalK = k;

                                innerView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ActionMenuItemView) innerView).getCompoundDrawables()[finalK].setColorFilter(colorFilter);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }
    }
}
