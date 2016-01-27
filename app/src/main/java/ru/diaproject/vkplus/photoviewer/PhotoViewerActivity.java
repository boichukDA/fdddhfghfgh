package ru.diaproject.vkplus.photoviewer;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.core.animations.SimpleAnimatorListener;
import ru.diaproject.vkplus.core.executor.SimpleTaskListener;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.BitmapUtils;
import ru.diaproject.vkplus.core.utils.EnumUtils;
import ru.diaproject.vkplus.core.utils.WindowUtils;
import ru.diaproject.vkplus.core.view.RobotoImageExpandableTextView;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.items.CommentsInfo;
import ru.diaproject.vkplus.news.model.items.LikesInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;
import ru.diaproject.vkplus.photoviewer.adapters.PhotoViewerAdapter;
import ru.diaproject.vkplus.photoviewer.transformers.DepthPageTransformer;
import ru.diaproject.vkplus.photoviewer.views.SimplePageChangeListener;
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

        animationSet.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                   createViewPager();
            }
        });

        animationSet.start();
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    private void createViewPager(){
        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        actionBar.setTitle((imagePos + 1) + " " + getResources().getString(R.string.main_string_of) + " " + photos.getCount());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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

                isShownInfo = false;
                topInitY = toolbar.getY();
                bottomInitY = bottomPanel.getY();

                toolbar.setVisibility(View.GONE);
                bottomPanel.setVisibility(View.GONE);

                loadPhotoData(photos.getPhotos().get(imagePos));
                imagePager.addOnPageChangeListener(new SimplePageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setTitle((position + 1) + " " + getResources().getString(R.string.main_string_of) + " " + photos.getPhotos().size());
                        loadPhotoData(photos.getPhotos().get(position));
                    }
                });
            }
        });
    }

    private void hideDescription() {
        if (!isShowDescription)
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
        animatorDescription.start();
    }

    private VKQuery<Photos> createQuery() {
        VKQuery<Photos> query = null;
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
        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.news_post_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        postLikeImage.setImageBitmap(bitmap);
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
                    }
                });
            }
        });

        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.news_post_comment, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        postCommentImage.setImageBitmap(bitmap);

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
                    }
                });
            }
        });
        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.news_share_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);
                    @Override
                    public void run() {
                        postShareImage.setImageBitmap(bitmap);
                        postShareLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }

                });
            }
        });

        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                PhotoViewerActivity.this.runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(PhotoViewerActivity.this, R.drawable.photo_viewer_info_white, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        photoViewerImage.setImageBitmap(bitmap);

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
                });
            }
        });
    }

    private void showDescription() {
        if (isShowDescription)
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
        animatorDescription.start();
    }

    public void showInfo(){
        if (isShownInfo)
            return;

        isShownInfo = true;

        ObjectAnimator animatorBottom = ObjectAnimator.ofFloat(bottomPanel, View.Y, bottomInitY+toolbar.getHeight(), bottomInitY);
        ObjectAnimator animatorTop = ObjectAnimator.ofFloat(toolbar, View.Y,  topInitY -toolbar.getHeight(), topInitY);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorBottom, animatorTop);
        set.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                toolbar.setVisibility(View.VISIBLE);
                bottomPanel.setVisibility(View.VISIBLE);
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
        set.addListener(new SimpleAnimatorListener() {
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
        });
        set.start();
    }
}
