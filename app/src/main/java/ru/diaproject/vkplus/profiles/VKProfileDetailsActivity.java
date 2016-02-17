package ru.diaproject.vkplus.profiles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.BitmapUtils;
import ru.diaproject.vkplus.core.utils.DataConstants;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.model.users.extusers.DataUserExt;
import ru.diaproject.vkplus.model.users.extusers.cropphoto.CropPhoto;
import ru.diaproject.vkplus.model.users.extusers.cropphoto.Rect;
import ru.diaproject.vkplus.profiles.adapters.UserDataAdapter;
import ru.diaproject.vkplus.profiles.model.UserDataContainer;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.customs.VKApi;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import rx.Observable;
import rx.functions.Action1;

public class VKProfileDetailsActivity extends ParentActivityNoTitle{
    private static final String USER_FIELDS = "photo_id,verified,sex,bdate,city,country,home_town,has_photo," +
            "photo_50,photo_100,photo_200,photo_max,photo_max_orig,online," +
            "contacts,site,universities,schools,status,occupation," +
            "common_count,nickname,relatives,relation,personal,wall_comments," +
            "activities,interests,music,movies,tv,books,games,about,quotes,can_post,can_see_all_posts,can_see_audio," +
            "can_write_private_message,can_send_friend_request,maiden_name,is_friend,friend_status,career,military,counters,crop_photo";

    private IDataUser owner;

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView mainImage;
    private RecyclerView dataList;
    private View mainLayout;

    @Override
    protected void initContent(Bundle savedInstanceState, Intent intent) {
        owner = (IDataUser) intent.getSerializableExtra(DataConstants.USER);
    }

    @Override
    protected void initBackend(final Bundle savedInstanceState) {
        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                VKQuery<DataUserExt> query = VKApi.userInfo(getUser()).get().with(VKParameter.USER_IDS, owner.getId()).and(VKParameter.FIELDS, USER_FIELDS).build();
                Observable.from(VKMainExecutor.request(query))
                        .subscribe(new Action1<DataUserExt>() {
                            @Override
                            public void call(DataUserExt dataUserExt) {
                                dataUserExt.setUser(owner);
                                UserDataContainer container = new UserDataContainer(dataUserExt, VKProfileDetailsActivity.this);
                                postInitUI(container, dataUserExt, savedInstanceState);
                            }
                        });
            }
        });

    }
    private void postInitUI(final UserDataContainer container, final DataUserExt userExt, Bundle savedInstanceState){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userExt.isHasPhoto()) {
                    CropPhoto photo = userExt.getCropPhoto();
                    if (photo != null) {
                        String urlPhoto = "".equals(photo.getPhoto().getPhoto807())
                                ?photo.getPhoto().getPhoto604()
                                :photo.getPhoto().getPhoto807();

                        Glide.with(VKProfileDetailsActivity.this).load(urlPhoto).asBitmap().into(new BitmapImageViewTarget(mainImage) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                Rect rect = userExt.getCropPhoto().getRect();

                                int width = resource.getWidth();
                                int height = resource.getHeight();

                                float x1 = (float) ((width * rect.getX()) / 100);
                                float x2 = width - (float) ((width * rect.getX2()) / 100);
                                x2 = width - (x1 + x2);

                                float y1 = (float) ((height * rect.getY()) / 100);
                                float y2 = height - (float) ((height * rect.getY2()) / 100);
                                y2 = height - (y1 + y2);

                                Bitmap cropped = Bitmap.createBitmap(resource, (int) x1, (int) y1, (int) x2, (int) y2);
                                super.setResource(cropped);
                            }
                        });
                    } else
                        Glide.with(VKProfileDetailsActivity.this).load(userExt.getPhotoMaxOrig()).into(mainImage);
                }

                UserDataAdapter adapter = new UserDataAdapter(VKProfileDetailsActivity.this, container, getColorScheme());
                dataList.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.profile_main_layout);

        mainLayout = findViewById(R.id.profile_main_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle(owner.getStringFullName());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == android.R.id.home) {
                    System.out.print("");
                }
                return true;
            }
        });
        mainImage = (ImageView) findViewById(R.id.profile_main_image);
        mainImage.setImageResource(owner.getPlaceholderResource());
        dataList = (RecyclerView) findViewById(R.id.profile_main_data_list);
        dataList.setLayoutManager(new LinearLayoutManager(this));
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
        collapsingToolbar.setCollapsedTitleTextColor(colorScheme.getTitleColor());
        collapsingToolbar.setExpandedTitleColor(colorScheme.getTitleColor());
        mainLayout.setBackgroundColor(colorScheme.getBackgroundColor());
    }

    @Override
    protected String getLogName() {
        return getClass().getSimpleName();
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Bitmap bitmap = BitmapUtils.appyColorFilter(this, R.drawable.news_post_new_white,
                getColorScheme().getTitleColor(), PorterDuff.Mode.MULTIPLY);

        menu.findItem(R.id.action_profile_write_message).setIcon(new BitmapDrawable(getResources(), bitmap));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }
}
