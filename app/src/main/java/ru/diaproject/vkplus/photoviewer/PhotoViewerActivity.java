package ru.diaproject.vkplus.photoviewer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.concurrent.CountDownLatch;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.executor.VKQueryTask;
import ru.diaproject.vkplus.core.utils.BitmapUtils;
import ru.diaproject.vkplus.core.utils.EnumUtils;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.photoviewer.adapters.PhotoViewerAdapter;
import ru.diaproject.vkplus.photoviewer.transformers.DepthPageTransformer;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.VkQueryBuilderException;

public class PhotoViewerActivity extends ParentActivityNoTitle{
    public static final String IMAGE_POSITION = "image_position";
    public static final String IMAGE_SOURCE = "image_source";
    public static final String IMAGE_DATE = "image_date";
    public static final String IMAGE_ARRAY = "image_array";

    public static final int ANIMATION_DURATION_MILLS = 500;

    private int imagePos;
    private Integer sourceId;
    private Integer date;
    private volatile Photos photos;
    private FilterType type;
    private CountDownLatch latch;
    private boolean isShownInfo;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private float topInitY;
    private float bottomInitY;

    @Bind(R.id.photo_viewer_background)
    View backgroundView;

    @Bind(R.id.photo_viewer_image_layout)
    ViewPager imagePager;

    @Bind(R.id.news_post_like_layout)
    public LinearLayout postLikeLayout;

    @Bind(R.id.news_post_like_image)
    public ImageView postLikeImage;

    @Bind(R.id.news_post_like_count)
    public RobotoTextView postLikeCount;

    @Bind(R.id.news_post_comment_layout)
    public LinearLayout postCommentLayout;

    @Bind(R.id.news_post_comment_image)
    public ImageView postCommentImage;

    @Bind(R.id.news_post_comment_count)
    public RobotoTextView postCommentCount;

    @Bind(R.id.news_post_share_layout)
    public LinearLayout postShareLayout;

    @Bind(R.id.news_post_share_image)
    public ImageView postShareImage;

    @Bind(R.id.news_post_share_count)
    public RobotoTextView postShareCount;

    @Bind(R.id.photo_viewer_info_image)
    public ImageView photoViewerImage;

    @Bind(R.id.photo_viewer_bottom_panel)
    public LinearLayout bottomPanel;

    @Bind(R.id.photo_viewer_top_layout)
    public RelativeLayout topLayout;
    private ActionBar actionBar;

    @Override
    protected void initContent(Bundle savedInstanceState, Intent activityIntent) {
        imagePos = activityIntent.getIntExtra(IMAGE_POSITION, 0);
        sourceId = activityIntent.getIntExtra(IMAGE_SOURCE, 0);
        date = activityIntent.getIntExtra(IMAGE_DATE, 0);
        photos = (Photos) activityIntent.getSerializableExtra(IMAGE_ARRAY);
        type = EnumUtils.deserialize(FilterType.class).from(activityIntent, FilterType.POST);

        latch = new CountDownLatch(1);
    }

    @Override
    protected void initBackend(Bundle savedInstanceState) {
        if (!photos.getCount().equals(photos.getPhotos().size())&& !type.equals(FilterType.POST))
        VKMainExecutor.INSTANCE.executeQuery(new VKQueryTask(this, createQuery(),
                new VKMainExecutor.VKTask.ITaskListener<Photos>(){
                    @Override
                    public void onDone(Photos result) {
                        PhotoViewerActivity.this.photos = result;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }, latch));
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.photo_viewer_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (photos.getCount().equals(photos.getPhotos().size()))
                actionBar.setTitle((imagePos + 1) +" " + getResources().getString(R.string.main_string_of)+ " "+ photos.getPhotos().size());
        }

        ObjectAnimator backgroundAnimator = ObjectAnimator.ofFloat(backgroundView, View.ALPHA, 0f, 1f);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(backgroundAnimator);
        animationSet.setDuration(ANIMATION_DURATION_MILLS);
        animationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!photos.getCount().equals(photos.getPhotos().size())&& !type.equals(FilterType.POST))
                 createViewPagerFromLoad();
                else
                   createViewPager();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animationSet.start();
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }
    private void createViewPagerFromLoad(){
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actionBar.setTitle((imagePos + 1) +" " + getResources().getString(R.string.main_string_of)+ " "+ photos.getCount());
        createViewPager();
    }
    private void createViewPager(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PhotoViewerAdapter adapter = new PhotoViewerAdapter(getSupportFragmentManager(), photos);
                imagePager.setPageTransformer(true, new DepthPageTransformer());
                imagePager.setAdapter(adapter);
                imagePager.setCurrentItem(imagePos);

                isShownInfo = false;
                topInitY = toolbar.getY();
                bottomInitY = bottomPanel.getY();

                toolbar.setVisibility(View.GONE);
                bottomPanel.setVisibility(View.GONE);
                topLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (isShownInfo)
                            hideInfo();
                        else
                            showInfo();
                    }
                });
                loadPhotoData();
                imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setTitle((position + 1) + " " + getResources().getString(R.string.main_string_of) + " " + photos.getPhotos().size());
                        loadPhotoData();
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
    }
    private VKQuery createQuery() {
        VKQuery query = null;
        VKQueryBuilder<Photos> builder = new VKQueryBuilder<>(getUser().getConfiguration());
        builder.setVKQueryType(VKQueryType.PHOTO);
        builder.setVKMethod(VKQuerySubMethod.DEFAULT);
        builder.setResultFormatType(VKQueryResponseTypes.JSON);
        builder.setVKResultType(Photos.class);
        builder.addCondition("owner_id", sourceId);
        builder.addCondition("feed", date);
        try {
            query = builder.build();
        } catch (VkQueryBuilderException e) {
            e.printStackTrace();
        }

        return query;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.photo_viewer_show, R.anim.photo_viewer_hide);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void loadPhotoData(){
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.news_post_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        postLikeImage.setImageBitmap(bitmap);
                    }
                });
            }
        });

        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.news_post_comment, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        postCommentImage.setImageBitmap(bitmap);
                    }
                });
            }
        });
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.news_share_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        postShareImage.setImageBitmap(bitmap);
                    }
                });
            }
        });

        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.photo_viewer_info_white, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        photoViewerImage.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public void showInfo(){
        if (isShownInfo)
            return;

        isShownInfo = true;

        ObjectAnimator animatorBottom = ObjectAnimator.ofFloat(bottomPanel, View.Y, bottomInitY+toolbar.getHeight(), bottomInitY);
        ObjectAnimator animatorTop = ObjectAnimator.ofFloat(toolbar, View.Y,  topInitY -toolbar.getHeight(), topInitY);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorBottom, animatorTop);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                toolbar.setVisibility(View.VISIBLE);
                bottomPanel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                toolbar.setVisibility(View.VISIBLE);
                bottomPanel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    public void hideInfo(){
        if (!isShownInfo)
            return;

        isShownInfo = false;
        ObjectAnimator animatorBottom = ObjectAnimator.ofFloat(bottomPanel, View.Y,  bottomInitY, bottomInitY+bottomPanel.getHeight());
        ObjectAnimator animatorTop = ObjectAnimator.ofFloat(toolbar, View.Y, topInitY, topInitY-toolbar.getHeight());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorBottom, animatorTop);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                toolbar.setVisibility(View.VISIBLE);
                bottomPanel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                toolbar.setVisibility(View.GONE);
                bottomPanel.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }
}
