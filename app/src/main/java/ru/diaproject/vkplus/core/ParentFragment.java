package ru.diaproject.vkplus.core;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.VkQueryBuilderException;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public abstract class ParentFragment extends Fragment implements ILoggable{
    private VKUser user;
    private DrawerImplementation impl;

    protected abstract void initBackend(Bundle savedInstanceState);

    protected abstract View initUI(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        if (user == null)
            throw new RuntimeException("User not come");

        Bundle bundle = getArguments();
        initContent(bundle);

        View returnView = null;
        try {
            returnView = initUI(inflater, container, savedInstanceState);
            VKMainExecutor.executeVKQuery(getContext(), createQuery(),
                    new SimpleTaskListener<User>() {
                        @Override
                        public void onDone(final User result) {
                            impl = new DrawerImplementation();

                            final Bitmap icon = BitmapUtils.loadBitmap(result.getPhoto100(), getContext());
                            impl.setFriendsSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.drawer_friends_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            impl.setPhotoSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.news_photo_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            impl.setVideoSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.news_video_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            impl.setAudioSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.news_drawer_audios_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            impl.setMessageSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.drawer_charts_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            impl.setGroupSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.news_group_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            impl.setNewsSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.news_group_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            impl.setSettingsSelectedBitmap(BitmapUtils.appyColorFilterForResource(getContext(),
                                    R.drawable.drawer_settings_white, com.mikepenz.materialdrawer.R.color.material_drawer_selected_text, PorterDuff.Mode.MULTIPLY));

                            ((Activity)getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initDrawer(result, icon);
                                }
                            });
                        }
                    });

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        initBackend(savedInstanceState);
                    } catch (final Throwable e) {
                        ((Activity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), getResources().getString(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();

        } catch (final Throwable e) {
            Log.e(getLogName(), "exception", e);
            ((Activity)getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), getResources().getString(R.string.fatal_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return returnView;
    }

    protected abstract void initContent(Bundle bundle);

    @Override
    public String getLogName() {
        return getClass().getSimpleName();
    }

    protected abstract Toolbar getToolbar();

    public void setUser(VKUser user) {
        this.user = user;
    }

    public VKUser getUser() {
        return user;
    }

    private void initDrawer(User result, Bitmap icon) {
        ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem();
        profileDrawerItem.withName(result.getFirstName()+" "+result.getLastName());
        profileDrawerItem.withEmail(result.getStatus());
        profileDrawerItem.withEnabled(false);

        if (icon!= null)
            profileDrawerItem.withIcon(icon);
        else profileDrawerItem.withIcon(ContextCompat.getDrawable(getContext(), R.drawable.man_siluette));

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.color.m_indigo)
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
        myFriendsItem.withIcon(R.drawable.drawer_friends_black);
        myFriendsItem.withTextColorRes(R.color.md_black_1000);
        myFriendsItem.withName(R.string.drawer_item_my_friends)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getFriendsSelectedBitmap()))
                .withIdentifier(1);

        PrimaryDrawerItem myPhotoItem = new PrimaryDrawerItem();
        myPhotoItem.withIcon(R.drawable.news_photo_black);
        myPhotoItem.withTextColorRes(R.color.md_black_1000);
        myPhotoItem.withName(R.string.drawer_item_my_photo)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getPhotoSelectedBitmap()))
                .withIdentifier(2);

        PrimaryDrawerItem myVideoItem = new PrimaryDrawerItem();
        myVideoItem.withIcon(R.drawable.news_video_black);
        myVideoItem.withTextColorRes(R.color.md_black_1000);
        myVideoItem.withName(R.string.drawer_item_my_video)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getVideoSelectedBitmap()))
                .withIdentifier(2);

        PrimaryDrawerItem myAudioItem = new PrimaryDrawerItem();
        myAudioItem.withIcon(R.drawable.news_drawer_audios_black);
        myAudioItem.withTextColorRes(R.color.md_black_1000);
        myAudioItem.withName(R.string.drawer_item_my_audio)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getAudioSelectedBitmap()))
                .withIdentifier(3);

        PrimaryDrawerItem myMessageItem = new PrimaryDrawerItem();
        myMessageItem.withIcon(R.drawable.drawer_charts_black);
        myMessageItem.withTextColorRes(R.color.md_black_1000);
        myMessageItem.withName(R.string.drawer_item_my_messages)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getMessageSelectedBitmap()))
                .withIdentifier(4);

        PrimaryDrawerItem myGroupItem = new PrimaryDrawerItem();
        myGroupItem.withIcon(R.drawable.news_group_black);
        myGroupItem.withTextColorRes(R.color.md_black_1000);
        myGroupItem.withName(R.string.drawer_item_my_group)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getGroupSelectedBitmap()))
                .withIdentifier(5);

        PrimaryDrawerItem myNewsItem = new PrimaryDrawerItem();
        myNewsItem.withIcon(R.drawable.drawer_news_black);
        myNewsItem.withTextColorRes(R.color.md_black_1000);
        myNewsItem.withName(R.string.drawer_item_my_news)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getNewsSelectedBitmap()))
                .withIdentifier(6);

        PrimaryDrawerItem mySettingsItem = new PrimaryDrawerItem();
        mySettingsItem.withIcon(R.drawable.drawer_settings_black);
        mySettingsItem.withTextColorRes(R.color.md_black_1000);
        mySettingsItem.withName(R.string.drawer_item_settings)
                .withSelectedIcon(new BitmapDrawable(getResources(), impl.getSettingsSelectedBitmap()))
                .withIdentifier(7);

        new DrawerBuilder()
                .withActivity(getActivity())
                .withToolbar(getToolbar())
                .withActionBarDrawerToggle(false)
                .withStatusBarColorRes(R.color.m_indigo)
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
    }

    private VKQuery<User> createQuery(){
        VKQuery<User> query = null;
        VKQueryBuilder<User> builder;
        try {
            builder = new VKQueryBuilder<>(user.getConfiguration());
            builder.setVKQueryType(VKQueryType.USERS);
            builder.setVKMethod(VKQuerySubMethod.DEFAULT);
            builder.setResultFormatType(VKQueryResponseTypes.JSON);
            builder.setVKResultType(User.class);
            builder.addCondition("fields", "city,verified,status,photo_100");
            builder.addCondition("user_ids", user.getAccountId());

            query = builder.build();
        } catch (VkQueryBuilderException e) {
            e.printStackTrace();
        }

        return query;
    }
}
