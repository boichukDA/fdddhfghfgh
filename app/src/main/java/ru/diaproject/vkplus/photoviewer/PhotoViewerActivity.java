package ru.diaproject.vkplus.photoviewer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devspark.robototextview.widget.RobotoTextView;


import java.util.ArrayList;
import java.util.List;

import ru.diaproject.ui.circularimageview.RobotoExpandableTextView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.core.animations.SimpleAnimatorListener;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.DataConstants;
import ru.diaproject.vkplus.core.utils.EnumUtils;
import ru.diaproject.vkplus.core.utils.WindowUtils;
import ru.diaproject.vkplus.core.view.ShadowLayout;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.news.binders.DataPhotosBinder;
import ru.diaproject.vkplus.model.newsitems.FilterType;
import ru.diaproject.vkplus.model.items.CommentsInfo;
import ru.diaproject.vkplus.model.items.LikesInfo;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.model.attachments.photos.PhotosInfo;
import ru.diaproject.vkplus.news.utils.PhotoConstants;
import ru.diaproject.vkplus.photoviewer.adapters.PhotoViewerAdapter;
import ru.diaproject.vkplus.photoviewer.model.ImageData;
import ru.diaproject.vkplus.photoviewer.transformers.DepthPageTransformer;
import ru.diaproject.vkplus.photoviewer.views.SimplePageChangeListener;
import ru.diaproject.vkplus.vkcore.queries.customs.VKApi;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import rx.Observable;
import rx.functions.Action1;

public class PhotoViewerActivity extends ParentActivityNoTitle {

    public static final int ANIMATION_DURATION_MILLS = 500;

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();

    private int imagePos;

    private volatile Photos oldPhotos;
    private FilterType type;
    private boolean isShownInfo;

    private float bottomInitY;

    Toolbar toolbar;
    View backgroundView;
    ViewPager imagePager;
    public LinearLayout postLikeLayout;
    public ImageView postLikeImage;
    public RobotoTextView postLikeCount;
    public LinearLayout postCommentLayout;
    public ImageView postCommentImage;
    public RobotoTextView postCommentCount;
    public LinearLayout postShareLayout;
    public ImageView postShareImage;
    public LinearLayout photoViewerInfoLayout;
    public ImageView photoViewerImage;
    public LinearLayout bottomPanel;
    private ActionBar actionBar;

    public LinearLayout descriptionLayout;
    public RobotoExpandableTextView descriptionText;

    private LinearLayout animationImageLayout;
    private ImageView animationImage;
    private View backView;
    private ShadowLayout shadowLayout;

    private float mWidthScale;
    private float mHeightScale;
    private float screeenCoef;
    private float imageCoef;

    private int ownerId;
    private Integer date;
    private List<ImageData> startImages;
    private ImageData enterStartImage;
    private ImageData enterEndImage;
    private ImageData exitEndImage;
    private ImageData exitStartImage;
    private boolean isEndAnimStarted = false;
    private View appBarLayout;


    @Override
    protected void initColorScheme(ColorScheme colorScheme) {
        super.initColorScheme(colorScheme);
        appBarLayout.setBackgroundColor(colorScheme.getMainColor());
        backView.setBackgroundColor(colorScheme.getCardColor());
    }

    @Override
    protected void initContent(Bundle savedInstanceState, Intent activityIntent) {
        imagePos = activityIntent.getIntExtra(PhotoConstants.IMAGE_POSITION, 0);
        oldPhotos = (Photos) activityIntent.getSerializableExtra(PhotoConstants.IMAGE_ARRAY);

        startImages = activityIntent.getParcelableArrayListExtra(PhotoConstants.IMAGE_VIEW_ARRAY);
        enterStartImage = startImages.get(imagePos);
        enterEndImage = new ImageData();

        ownerId = activityIntent.getIntExtra(DataConstants.OWNER_ID, 0);
        date = activityIntent.getIntExtra(DataConstants.DATE, 0);
        type = EnumUtils.deserialize(FilterType.class).from(activityIntent, FilterType.POST);

        screeenCoef = (float)getResources().getDisplayMetrics().heightPixels/getResources().getDisplayMetrics().widthPixels;

        PhotosInfo currentPhoto = oldPhotos.getPhotos().get(imagePos);

        imageCoef = (float)currentPhoto.getHeight()/currentPhoto.getWidth();

        float coef;
        if (imageCoef >= screeenCoef){
            coef = (float) enterStartImage.getHeight()/getResources().getDisplayMetrics().heightPixels;

            enterEndImage.setWidth((int) (enterStartImage.getWidth() / coef));
            enterEndImage.setHeight((int) (enterStartImage.getHeight() / coef));

            enterEndImage.setY(0);
            enterEndImage.setX((getResources().getDisplayMetrics().widthPixels - enterEndImage.getWidth())/2);
        }else{
            coef = (float) enterStartImage.getWidth()/getResources().getDisplayMetrics().widthPixels;
            enterEndImage.setWidth((int) (enterStartImage.getWidth() / coef));
            enterEndImage.setHeight((int) (enterStartImage.getHeight() / coef));

            enterEndImage.setX(0);
            enterEndImage.setY((getResources().getDisplayMetrics().heightPixels - enterEndImage.getHeight()- WindowUtils.getStatusBarHeight(this))/2);
        }
    }

    @Override
    protected void initBackend(Bundle savedInstanceState) {
        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                if (type.equals(FilterType.POST)) {
                    Observable.from(VKMainExecutor
                            .request(VKApi.photos(getUser()).getById()
                                    .with(VKParameter.PHOTOS, oldPhotos)
                                    .and(VKParameter.EXTENDED, 1)
                                    .build()))
                            .subscribe(new Action1<Photos>() {
                                @Override
                                public void call(final Photos photos) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            prepareFullData(photos);
                                        }
                                    });
                                }
                            });
                } else
                    Observable.from(VKMainExecutor
                            .request(VKApi.photos(getUser()).get()
                                    .with(VKParameter.FEED, date)
                                    .and(VKParameter.EXTENDED, 1)
                                    .and(VKParameter.FEED_TYPE, type.toString().toLowerCase())
                                    .and(VKParameter.OWNER_ID, ownerId)
                                    .build()))
                            .subscribe(new Action1<Photos>() {
                                @Override
                                public void call(final Photos photos) {
                                    prepareFullData(photos);

                                }
                            });
            }
        });
    }

    private void prepareFullData(Photos photos) {
        oldPhotos = mergePhotos(oldPhotos, photos);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((PhotoViewerAdapter) imagePager.getAdapter()).setPhotos(oldPhotos);
                (imagePager.getAdapter()).notifyDataSetChanged();
                loadPhotoData(oldPhotos.getPhotos().get(imagePager.getCurrentItem()));
            }
        });
    }

    private Photos mergePhotos(Photos oldPhotos, Photos photos) {
        Photos resultPhotos = new Photos();
        List<PhotosInfo> infoes = new ArrayList<>();

        resultPhotos.setCount(oldPhotos.getCount());
        for (PhotosInfo info:oldPhotos.getPhotos()) {
            infoes.add(photos.getPhotos().remove(photos.getPhotos().indexOf(info)));
        }
        infoes.addAll(photos.getPhotos());
        resultPhotos.setPhotos(infoes);
        return resultPhotos;
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.photo_viewer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar_layout);
        backgroundView = findViewById(R.id.photo_viewer_background);

        imagePager = (ViewPager) findViewById(R.id.photo_viewer_image_layout);
        postLikeLayout = (LinearLayout) findViewById(R.id.news_post_like_layout);
        postLikeImage = (ImageView) findViewById(R.id.news_post_like_image);

        postLikeCount = (RobotoTextView) findViewById(R.id.news_post_like_count);

        postCommentLayout = (LinearLayout) findViewById(R.id.news_post_comment_layout);
        postCommentImage = (ImageView) findViewById(R.id.news_post_comment_image);
        postCommentCount = (RobotoTextView) findViewById(R.id.news_post_comment_count);
        postShareLayout = (LinearLayout) findViewById(R.id.news_post_share_layout);

        postShareImage = (ImageView) findViewById(R.id.news_post_share_image);
        photoViewerInfoLayout = (LinearLayout) findViewById(R.id.photo_viewer_info_layout);
        photoViewerImage = (ImageView) findViewById(R.id.photo_viewer_info_image);
        bottomPanel = (LinearLayout) findViewById(R.id.photo_viewer_bottom_panel);

        descriptionLayout = (LinearLayout) findViewById(R.id.photo_viewer_description_layout);
        descriptionText = (RobotoExpandableTextView) findViewById(R.id.photo_viewer_description_text);

        animationImageLayout = (LinearLayout) findViewById(R.id.photo_viewer_animation_layout);
        animationImage = (ImageView) findViewById(R.id.photo_viewer_animation_view);
        shadowLayout = (ShadowLayout) findViewById(R.id.photo_viewer_shadow_layout);
        backView = findViewById(R.id.photo_viewer_animation_back_view);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (oldPhotos.getCount().equals(oldPhotos.getPhotos().size()))
                actionBar.setTitle((imagePos + 1) +" " + getResources().getString(R.string.main_string_of)+ " "+ oldPhotos.getCount());
        }

        toolbar.setVisibility(View.GONE);
        bottomPanel.setVisibility(View.INVISIBLE);

        PhotoViewerAdapter adapter = new PhotoViewerAdapter(getSupportFragmentManager(), oldPhotos, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShownInfo)
                    hideInfo();
                else
                    showInfo();
            }
        });
        imagePager.setPageTransformer(true, new DepthPageTransformer());
        imagePager.setAdapter(adapter);
        imagePager.setCurrentItem(imagePos);

        animationImage.setX(enterEndImage.getX());
        animationImage.setY(enterEndImage.getY());
        animationImage.getLayoutParams().width = enterEndImage.getWidth();
        animationImage.getLayoutParams().height = enterEndImage.getHeight();

        ViewTreeObserver observer = animationImage.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                animationImage.getViewTreeObserver().removeOnPreDrawListener(this);
                Glide.with(PhotoViewerActivity.this).load(oldPhotos.getPhotos().get(imagePos).getPhoto604()).dontAnimate().listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        backView.setX(enterStartImage.getX());
                        backView.setY(enterStartImage.getY() + enterStartImage.getVisibleHeightStart());
                        backView.getLayoutParams().width = enterStartImage.getWidth();
                        backView.getLayoutParams().height = (enterStartImage.getVisibleHeightEnd() - enterStartImage.getVisibleHeightStart());
                        backView.setVisibility(View.VISIBLE);
                        runEnterAnimation();
                        return false;
                    }
                }).into(animationImage);

                return true;
            }
        });
    }

    private void runEnterAnimation() {
        animationImage.setVisibility(View.VISIBLE);

        mWidthScale = (float) enterStartImage.getWidth() / enterEndImage.getWidth();
        mHeightScale = (float) enterStartImage.getHeight() / enterEndImage.getHeight();

        animationImage.setPivotX(0);
        animationImage.setPivotY(0);
        animationImage.setScaleX(mWidthScale);
        animationImage.setScaleY(mHeightScale);
        animationImage.setTranslationX(enterStartImage.getX());
        animationImage.setTranslationY(enterStartImage.getY());

        animationImage.animate().setDuration(ANIMATION_DURATION_MILLS).
                scaleX(1).scaleY(1)
                .translationX(enterEndImage.getX()).translationY(enterEndImage.getY())
                .setInterpolator(sDecelerator).setListener(new SimpleAnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animation) {
                AnimatorSet set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(backgroundView, "alpha", 0, 1),
                        ObjectAnimator.ofFloat(shadowLayout, "shadowMove", 0, 1));
                set.setDuration(ANIMATION_DURATION_MILLS);
                set.addListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imagePager.setVisibility(View.VISIBLE);
                        animationImageLayout.setVisibility(View.GONE);
                        backView.setVisibility(View.GONE);
                        backgroundView.setVisibility(View.GONE);
                        initHelpData();
                    }
                });
                set.start();
            }

        });

        ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(shadowLayout, "shadowDepth", 0, 1);
        shadowAnim.setDuration(ANIMATION_DURATION_MILLS);
        shadowAnim.start();
    }

    private void initHelpData() {
        actionBar.setTitle((imagePos + 1) + " " + getResources().getString(R.string.main_string_of) + " " + oldPhotos.getCount());
        isShownInfo = false;

        ViewTreeObserver observer = bottomPanel.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                bottomPanel.getViewTreeObserver().removeOnPreDrawListener(this);
                bottomInitY = bottomPanel.getY();
                bottomPanel.setVisibility(View.GONE);
                return true;
            }
        });


        toolbar.setVisibility(View.GONE);
        bottomPanel.setVisibility(View.GONE);

        loadPhotoData(oldPhotos.getPhotos().get(imagePos));
        imagePager.addOnPageChangeListener(new SimplePageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setTitle((position + 1) + " " + getResources().getString(R.string.main_string_of) + " " + oldPhotos.getCount());
                if (position < oldPhotos.getPhotos().size())
                    loadPhotoData(oldPhotos.getPhotos().get(position));
            }
        });
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.photo_viewer_show, R.anim.photo_viewer_hide);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isShownInfo)
            bottomInitY = getResources().getDisplayMetrics().heightPixels - bottomPanel.getHeight();
        else
            bottomInitY = getResources().getDisplayMetrics().heightPixels - bottomPanel.getHeight();
        bottomInitY-= WindowUtils.getStatusBarHeight(this);
    }

    private void loadPhotoData(final PhotosInfo currentPhoto){
        postLikeImage.setImageResource(R.drawable.news_post_like);
        LikesInfo likes = currentPhoto.getLikes();

        if (likes!=null) {
            postLikeCount.setText(String.valueOf(likes.getCount()));
            if (likes.getUserLikes())
                ViewCompat.setAlpha(postLikeImage, 0.5f);
            else {
                ViewCompat.setAlpha(postLikeImage, 1f);
                postLikeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }

        postCommentImage.setImageResource(R.drawable.news_post_comment);

        CommentsInfo commentsInfo = currentPhoto.getComments();
        if (commentsInfo != null) {
            postCommentCount.setText(String.valueOf(commentsInfo.getCount()));
            if (!currentPhoto.isCanComment())
                postCommentLayout.setVisibility(View.INVISIBLE);
            else postCommentLayout.setVisibility(View.VISIBLE);
        }
        postCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        postShareImage.setImageResource(R.drawable.news_share_like);
        postShareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        photoViewerImage.setImageResource(R.drawable.photo_viewer_info_white);

        isShownInfo = false;
        if ("".equals(currentPhoto.getText()))
            photoViewerInfoLayout.setVisibility(View.GONE);
    }

    public void showInfo(){
        if (isShownInfo)
            return;

        isShownInfo = true;

        bottomPanel.animate().y(bottomInitY)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        bottomPanel.setVisibility(View.VISIBLE);
                    }
                });
        toolbar.animate().y(0)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbar.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void hideInfo(){
        if (!isShownInfo)
            return;

        isShownInfo = false;
        bottomPanel.animate().y(bottomPanel.getHeight() + bottomInitY)
                .setListener(new SimpleAnimatorListener(){
                    @Override
                    public void onAnimationStart(Animator animation) {
                        bottomPanel.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        bottomPanel.setVisibility(View.GONE);
                    }
                });
        toolbar.animate().y(-toolbar.getHeight())
                .setListener(new SimpleAnimatorListener(){
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (getDrawer()!= null && getDrawer().isDrawerOpen())
            getDrawer().closeDrawer();
        else  if (isShownInfo)
            hideInfo();
        else if (imagePager.getCurrentItem() < DataPhotosBinder.MAX_PHOTOS_DISPLAY){
            if (!isEndAnimStarted) {
                isEndAnimStarted = true;
                exitWithAnimation();
            }
        }else
            super.onBackPressed();
    }

    private void exitWithAnimation() {
        int itemPosition = imagePager.getCurrentItem();
        exitStartImage = new ImageData();
        exitEndImage = startImages.get(itemPosition);

        PhotosInfo currentPhoto = oldPhotos.getPhotos().get(itemPosition);

        imageCoef = (float)currentPhoto.getHeight()/currentPhoto.getWidth();

        float coef;
        if (imageCoef >= screeenCoef){
            coef = (float) exitEndImage.getHeight()/getResources().getDisplayMetrics().heightPixels;

            exitStartImage.setWidth((int) (exitEndImage.getWidth() / coef));
            exitStartImage.setHeight((int) (exitEndImage.getHeight() / coef));

            exitStartImage.setY(0);
            exitStartImage.setX((getResources().getDisplayMetrics().widthPixels - exitStartImage.getWidth()) / 2);
        }else{
            coef = (float) exitEndImage.getWidth()/getResources().getDisplayMetrics().widthPixels;
            exitStartImage.setWidth((int) (exitEndImage.getWidth() / coef));
            exitStartImage.setHeight((int) (exitEndImage.getHeight() / coef));

            exitStartImage.setX(0);
            exitStartImage.setY((getResources().getDisplayMetrics().heightPixels - exitStartImage.getHeight() - WindowUtils.getStatusBarHeight(this)) / 2);
        }

        animationImage.setX(exitStartImage.getX());
        animationImage.setY(exitStartImage.getY());
        animationImage.getLayoutParams().width = exitStartImage.getWidth();
        animationImage.getLayoutParams().height = exitStartImage.getHeight();

        Glide.with(PhotoViewerActivity.this).load(oldPhotos.getPhotos().get(itemPosition).getPhoto604()).dontAnimate().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                backView.setX(exitEndImage.getX());
                backView.setY(exitEndImage.getY() + exitEndImage.getVisibleHeightStart());
                backView.getLayoutParams().width = exitEndImage.getWidth();
                backView.getLayoutParams().height = (exitEndImage.getVisibleHeightEnd() - exitEndImage.getVisibleHeightStart());
                backView.setVisibility(View.VISIBLE);

                animationImage.setVisibility(View.VISIBLE);
                imagePager.setVisibility(View.GONE);
                animationImageLayout.setVisibility(View.VISIBLE);
                backgroundView.setVisibility(View.VISIBLE);
                shadowLayout.setVisibility(View.VISIBLE);
                backView.setVisibility(View.VISIBLE);
                runExitAnimation();
                return false;
            }
        }).into(animationImage);


    }

    private void runExitAnimation() {
        mWidthScale = (float) exitStartImage.getWidth() / exitEndImage.getWidth();
        mHeightScale = (float) exitStartImage.getHeight() / exitEndImage.getHeight();

        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(backgroundView, "alpha", 1, 0),
                ObjectAnimator.ofFloat(shadowLayout, "shadowMove", 1, 0));
        set.setDuration(ANIMATION_DURATION_MILLS);
        set.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationImage.setPivotX(0);
                animationImage.setPivotY(0);
                animationImage.setScaleX(1);
                animationImage.setScaleY(1);
                animationImage.setTranslationX(exitStartImage.getX());
                animationImage.setTranslationY(exitStartImage.getY());

                animationImage.animate().setDuration(ANIMATION_DURATION_MILLS).
                        scaleX(1 / mWidthScale).scaleY(1 / mHeightScale)
                        .translationX(exitEndImage.getX()).translationY(exitEndImage.getY())
                        .setInterpolator(sDecelerator).setListener(new SimpleAnimatorListener() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imagePager.setVisibility(View.GONE);
                        animationImageLayout.setVisibility(View.GONE);
                        backgroundView.setVisibility(View.GONE);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                });
                ObjectAnimator shadowAnim = ObjectAnimator.ofFloat(shadowLayout, "shadowDepth", 1, 0);
                shadowAnim.setDuration(ANIMATION_DURATION_MILLS);
                shadowAnim.start();
            }

        });
        set.start();
    }
}
