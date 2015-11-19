package ru.diaproject.vkplus.photoviewer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.concurrent.CountDownLatch;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.executor.VKQueryTask;
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

    public static final int ANIMATION_DURATION_MILLS = 500;

    private int imagePos;
    private Integer sourceId;
    private Integer date;
    private volatile Photos photos;
    private CountDownLatch latch;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.photo_viewer_background)
    View backgroundView;

    @Bind(R.id.photo_viewer_image_layout)
    ViewPager imagePager;

    private ActionBar actionBar;

    @Override
    protected void initContent(Bundle savedInstanceState, Intent activityIntent) {
        imagePos = activityIntent.getIntExtra(IMAGE_POSITION, 0);
        sourceId = activityIntent.getIntExtra(IMAGE_SOURCE, 0);
        date = activityIntent.getIntExtra(IMAGE_DATE, 0);
        latch = new CountDownLatch(1);
    }

    @Override
    protected void initBackend(Bundle savedInstanceState) {
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

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.photo_viewer_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle((imagePos + 1) +" " + getResources().getString(R.string.main_string_of)+ " ");
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
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
    private void createViewPager(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PhotoViewerAdapter adapter = new PhotoViewerAdapter(getSupportFragmentManager(), photos);
                imagePager.setPageTransformer(true, new DepthPageTransformer());
                imagePager.setAdapter(adapter);
                imagePager.setCurrentItem(imagePos);
                imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setTitle((position + 1) + " " + getResources().getString(R.string.main_string_of) + " " + photos.getCount() );
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
}
