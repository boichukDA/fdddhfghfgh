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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.concurrent.CountDownLatch;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.core.animations.SimpleAnimatorListener;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.DataConstants;
import ru.diaproject.vkplus.core.utils.EnumUtils;
import ru.diaproject.vkplus.core.utils.WindowUtils;
import ru.diaproject.vkplus.core.view.RobotoImageExpandableTextView;
import ru.diaproject.vkplus.core.view.ShadowLayout;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.items.CommentsInfo;
import ru.diaproject.vkplus.news.model.items.LikesInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;
import ru.diaproject.vkplus.news.utils.PhotoConstants;
import ru.diaproject.vkplus.photoviewer.adapters.PhotoViewerAdapter;
import ru.diaproject.vkplus.photoviewer.transformers.DepthPageTransformer;
import ru.diaproject.vkplus.photoviewer.views.SimplePageChangeListener;
import ru.diaproject.vkplus.vkcore.queries.VKQuery;
import ru.diaproject.vkplus.vkcore.queries.VKQueryBuilder;
import ru.diaproject.vkplus.vkcore.queries.VKQueryResponseTypes;
import ru.diaproject.vkplus.vkcore.queries.VKQuerySubMethod;
import ru.diaproject.vkplus.vkcore.queries.VKQueryType;
import ru.diaproject.vkplus.vkcore.queries.VkQueryBuilderException;
import ru.diaproject.vkplus.vkcore.queries.customs.VKApi;
import ru.diaproject.vkplus.vkcore.queries.customs.VKParameter;
import rx.Observable;
import rx.functions.Action1;

public class PhotoViewerActivity extends ParentActivityNoTitle {

    public static final int ANIMATION_DURATION_MILLS = 500;

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();

    private int imagePos;

    private volatile Photos photos;
    private FilterType type;
    private CountDownLatch latch;
    private boolean isShownInfo;
    private boolean isShowDescription;

    private float topInitY;
    private float bottomInitY;
    private int descriptionHeight;

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
    public RobotoImageExpandableTextView descriptionText;

    private LinearLayout animationImageLayout;
    private ImageView animationImage;
    private View backView;
    private ShadowLayout shadowLayout;

    private int startViewX;
    private int startViewY;
    private int startViewWidth;
    private int startViewHeight;

    private int endViewX;
    private int endViewY;
    private int endViewWidth;
    private int endViewHeight;

    private float density;
    private float mWidthScale;
    private float mHeightScale;
    private float screeenCoef;
    private float imageCoef;
    private int visibleHeightStart;
    private int visibleHeightEnd;
    private int ownerId;
    private Integer date;

    @Override
    protected void initContent(Bundle savedInstanceState, Intent activityIntent) {
        imagePos = activityIntent.getIntExtra(PhotoConstants.IMAGE_POSITION, 0);
        photos = (Photos) activityIntent.getSerializableExtra(PhotoConstants.IMAGE_ARRAY);

        startViewX = activityIntent.getIntExtra(PhotoConstants.IMAGE_X, 0);
        startViewY = activityIntent.getIntExtra(PhotoConstants.IMAGE_Y, 0) - WindowUtils.getStatusBarHeight(this);
        startViewWidth = activityIntent.getIntExtra(PhotoConstants.IMAGE_WIDTH, 0);
        startViewHeight = activityIntent.getIntExtra(PhotoConstants.IMAGE_HEIGHT, 0);
        visibleHeightStart = activityIntent.getIntExtra(PhotoConstants.IMAGE_VISIBLE_HEIGHT_START, 0);
        visibleHeightEnd = activityIntent.getIntExtra(PhotoConstants.IMAGE_VISIBLE_HEIGHT_END, 0);
        ownerId = activityIntent.getIntExtra(DataConstants.OWNER_ID, 0);
        date = activityIntent.getIntExtra(DataConstants.DATE, 0);
        type = EnumUtils.deserialize(FilterType.class).from(activityIntent, FilterType.POST);

        density = getResources().getDisplayMetrics().density;
        screeenCoef = (float)getResources().getDisplayMetrics().heightPixels/getResources().getDisplayMetrics().widthPixels;

        PhotosInfo currentPhoto = photos.getPhotos().get(imagePos);

        imageCoef = (float)currentPhoto.getHeight()/currentPhoto.getWidth();

        float coef;
        if (imageCoef >= screeenCoef){
            coef = (float) startViewHeight/getResources().getDisplayMetrics().heightPixels;

            endViewWidth = (int) (startViewWidth/coef);
            endViewHeight = (int) (startViewHeight/coef);

            endViewY = 0;
            endViewX = (getResources().getDisplayMetrics().widthPixels - endViewWidth)/2;
        }else{
            coef = (float) startViewWidth/getResources().getDisplayMetrics().widthPixels;

            endViewWidth = (int) (startViewWidth/coef);
            endViewHeight = (int) (startViewHeight/coef);

            endViewX = 0;
            endViewY = (getResources().getDisplayMetrics().heightPixels - endViewHeight- WindowUtils.getStatusBarHeight(this))/2;
        }
    }

    @Override
    protected void initBackend(Bundle savedInstanceState) {
        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                Observable.from(VKMainExecutor
                        .request(VKApi.photos(getUser()).get()
                                .with(VKParameter.FEED, date)
                                .and(VKParameter.EXTENDED, 1)
                                .and(VKParameter.OWNER_ID, ownerId)
                                .build()))
                        .subscribe(new Action1<Photos>() {
                            @Override
                            public void call(final Photos photos) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadPhotoData(photos.getPhotos().get(imagePager.getCurrentItem()));
                                    }
                                });
                            }
                        });
            }
        });

        ;
       /* if (!photos.getCount().equals(photos.getPhotos().size())&& !type.equals(FilterType.POST)) {
            latch = new CountDownLatch(2);
            VKMainExecutor.executeVKQuery(createQuery(),
                    new SimpleTaskListener<Photos>() {
                        @Override
                        public void onDone(Photos result) {
                            VKMainExecutor.executeVKQuery( createPhotoQuery(result),
                                    new SimpleTaskListener<Photos>() {
                                        @Override
                                        public void onDone(Photos result) {
                                            PhotoViewerActivity.this.photos = result;
                                        }

                                    }, latch);
                        }

                    }, latch);
        }
        else VKMainExecutor.executeVKQuery(createPhotoQuery(photos),
                new SimpleTaskListener<Photos>() {
                    @Override
                    public void onDone(Photos result) {
                        PhotoViewerActivity.this.photos = result;
                    }

                }, latch);*/
    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setContentView(R.layout.photo_viewer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        descriptionText = (RobotoImageExpandableTextView) findViewById(R.id.photo_viewer_description_text);

        animationImageLayout = (LinearLayout) findViewById(R.id.photo_viewer_animation_layout);
        animationImage = (ImageView) findViewById(R.id.photo_viewer_animation_view);
        shadowLayout = (ShadowLayout) findViewById(R.id.photo_viewer_shadow_layout);
        backView = findViewById(R.id.photo_viewer_animation_back_view);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (photos.getCount().equals(photos.getPhotos().size()))
                actionBar.setTitle((imagePos + 1) +" " + getResources().getString(R.string.main_string_of)+ " "+ photos.getCount());
        }

        toolbar.setVisibility(View.GONE);
        bottomPanel.setVisibility(View.INVISIBLE);

        PhotoViewerAdapter adapter = new PhotoViewerAdapter(getSupportFragmentManager(), photos, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShownInfo) {
                    if (descriptionLayout.getVisibility() == View.VISIBLE) {
                        hideDescription();
                        return;
                    }
                    hideInfo();
                } else
                    showInfo();
            }
        });
        imagePager.setPageTransformer(true, new DepthPageTransformer());
        imagePager.setAdapter(adapter);
        imagePager.setCurrentItem(imagePos);

        animationImage.setX(endViewX);
        animationImage.setY(endViewY);
        animationImage.getLayoutParams().width = endViewWidth;
        animationImage.getLayoutParams().height = endViewHeight;

        ViewTreeObserver observer = animationImage.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                animationImage.getViewTreeObserver().removeOnPreDrawListener(this);
                Glide.with(PhotoViewerActivity.this).load(photos.getPhotos().get(imagePos).getPhoto604()).dontAnimate().listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        backView.setX(startViewX);
                        backView.setY(startViewY+ visibleHeightStart);
                        backView.getLayoutParams().width = startViewWidth;
                        backView.getLayoutParams().height = (visibleHeightEnd - visibleHeightStart);
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

        mWidthScale = (float) startViewWidth / endViewWidth;
        mHeightScale = (float) startViewHeight / endViewHeight;

        animationImage.setPivotX(0);
        animationImage.setPivotY(0);
        animationImage.setScaleX(mWidthScale);
        animationImage.setScaleY(mHeightScale);
        animationImage.setTranslationX(startViewX);
        animationImage.setTranslationY(startViewY);

        animationImage.animate().setDuration(ANIMATION_DURATION_MILLS).
                scaleX(1).scaleY(1).translationX(endViewX).translationY(endViewY)
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
        actionBar.setTitle((imagePos + 1) + " " + getResources().getString(R.string.main_string_of) + " " + photos.getCount());
        isShownInfo = false;
        topInitY = toolbar.getY();

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

        loadPhotoData(photos.getPhotos().get(imagePos));
        imagePager.addOnPageChangeListener(new SimplePageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setTitle((position + 1) + " " + getResources().getString(R.string.main_string_of) + " " + photos.getCount());
                if (position < photos.getPhotos().size())
                    loadPhotoData(photos.getPhotos().get(position));
            }
        });
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }


    private void hideDescription() {
        /*if (!isShowDescription)
            return;

        ObjectAnimator animatorDescription = ObjectAnimator.ofFloat(descriptionLayout, View.Y, descriptionLayout.getY(), descriptionLayout.getY()+descriptionLayout.getHeight());
        animatorDescription.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                descriptionLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                descriptionLayout.setVisibility(View.GONE);
                isShowDescription = false;
            }
        });
        animatorDescription.start();*/
    }

    private VKQuery<Photos> createQuery() {
        VKApi.photos(getUser()).get().with(VKParameter.FEED, date).and(VKParameter.EXTENDED, 1).build();
        VKQuery<Photos> query = null;
        VKQueryBuilder<Photos> builder = new VKQueryBuilder<>(getUser().getConfiguration());
        builder.setVKQueryType(VKQueryType.PHOTO);
        builder.setVKMethod(VKQuerySubMethod.DEFAULT);
        builder.setResultFormatType(VKQueryResponseTypes.JSON);
        builder.setVKResultType(Photos.class);
      //  builder.addCondition("owner_id", sourceId);
        builder.addCondition("feed", date);
        try {
            query = builder.build();
        } catch (VkQueryBuilderException e) {
            e.printStackTrace();
        }

        return query;
    }

    private VKQuery<Photos> createPhotoQuery(Photos oldPhotos) {
        StringBuilder queryParamBuilder = new StringBuilder();
        for (PhotosInfo info:oldPhotos.getPhotos()){
            queryParamBuilder.append(info.getOwnerId());
            queryParamBuilder.append("_");
            queryParamBuilder.append(info.getId());
            queryParamBuilder.append("_");
            queryParamBuilder.append(info.getAccessToken());
            queryParamBuilder.append(",");
        }
        queryParamBuilder.deleteCharAt(queryParamBuilder.length() - 1);

        VKQuery<Photos> query = null;
        VKQueryBuilder<Photos> builder = new VKQueryBuilder<>(getUser().getConfiguration());
        builder.setVKQueryType(VKQueryType.PHOTO);
        builder.setVKMethod(VKQuerySubMethod.GET_BY_ID);
        builder.setResultFormatType(VKQueryResponseTypes.JSON);
        builder.setVKResultType(Photos.class);
        builder.addCondition("photos", queryParamBuilder.toString());
        builder.addCondition("extended", 1);

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
        else {
            descriptionText.setText(currentPhoto.getText());
            descriptionLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {

                        @Override
                        public void onGlobalLayout() {
                            descriptionHeight = descriptionLayout.getHeight();
                            descriptionLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            descriptionLayout.setVisibility(View.GONE);
                        }

                    });
            photoViewerInfoLayout.setVisibility(View.VISIBLE);
        }
        photoViewerInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descriptionLayout.getVisibility() == View.VISIBLE)
                    hideDescription();
                else showDescription();
            }
        });
    }

    private void showDescription() {
       /* if (isShowDescription)
            return;

        ObjectAnimator animatorDescription = ObjectAnimator.ofFloat(descriptionLayout, View.Y, bottomPanel.getY()-bottomPanel.getHeight(), bottomPanel.getY()-bottomPanel.getHeight()-descriptionHeight);
        animatorDescription.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                descriptionLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isShowDescription = true;
            }
        });
        animatorDescription.start();*/
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
}
