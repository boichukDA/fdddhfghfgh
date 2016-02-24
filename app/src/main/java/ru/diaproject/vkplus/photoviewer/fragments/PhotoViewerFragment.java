package ru.diaproject.vkplus.photoviewer.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import java.util.concurrent.CountDownLatch;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentFragment;
import ru.diaproject.vkplus.core.animations.SimpleAnimatorListener;
import ru.diaproject.vkplus.core.executor.SimpleTaskListener;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.BitmapUtils;
import ru.diaproject.vkplus.core.utils.EnumUtils;
import ru.diaproject.vkplus.model.newsitems.FilterType;
import ru.diaproject.vkplus.model.items.CommentsInfo;
import ru.diaproject.vkplus.model.items.LikesInfo;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.model.attachments.photos.PhotosInfo;
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

public class PhotoViewerFragment extends ParentFragment{
    private static final PhotoViewerFragment fragment = new PhotoViewerFragment();
    public static final PhotoViewerFragment getInstance(){
        return fragment;
    }

    public static final String IMAGE_POSITION = PhotoConstants.IMAGE_POSITION;
    //public static final String IMAGE_SOURCE = PhotoConstants.IMAGE_SOURCE;
    public static final String IMAGE_DATE = PhotoConstants.IMAGE_DATE;
    public static final String IMAGE_ARRAY = PhotoConstants.IMAGE_ARRAY;

    public static final int ANIMATION_DURATION_MILLS = 500;

    private int imagePos;
    private Integer sourceId;
    private Integer date;
    private volatile Photos photos;
    private FilterType type;
    private CountDownLatch latch;
    private boolean isShownInfo;

    private Toolbar toolbar;

    private float topInitY;
    private float bottomInitY;

    View backgroundView;
    ViewPager imagePager;
    private LinearLayout postLikeLayout;
    private ImageView postLikeImage;
    private RobotoTextView postLikeCount;
    private LinearLayout postCommentLayout;
    private ImageView postCommentImage;
    private RobotoTextView postCommentCount;
    private LinearLayout postShareLayout;
    private ImageView postShareImage;
    private RobotoTextView postShareCount;
    private ImageView photoViewerImage;
    private LinearLayout bottomPanel;
    private RelativeLayout topLayout;

    private ActionBar actionBar;

    @Override
    protected void initBackend(Bundle savedInstanceState) {
        if (!photos.getCount().equals(photos.getPhotos().size())&& !type.equals(FilterType.POST)) {
            latch = new CountDownLatch(2);
            VKMainExecutor.executeVKQuery(createQuery(),
                    new SimpleTaskListener<Photos>() {
                        @Override
                        public void onDone(Photos result) {
                            VKMainExecutor.executeVKQuery(createPhotoQuery(result),
                                    new SimpleTaskListener<Photos>() {
                                        @Override
                                        public void onDone(Photos result) {
                                            PhotoViewerFragment.this.photos = result;
                                        }

                                    }, latch);
                        }

                    }, latch);
        }
        else VKMainExecutor.executeVKQuery( createPhotoQuery(photos),
                new SimpleTaskListener<Photos>() {
                    @Override
                    public void onDone(Photos result) {
                        PhotoViewerFragment.this.photos = result;
                    }

                }, latch);
    }

    @Override
    protected View initUI(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.photo_viewer_layout, container, false);

      //  backgroundView = rootView.findViewById(R.id.photo_viewer_background);

        imagePager = (ViewPager) rootView.findViewById(R.id.photo_viewer_image_layout);
        postLikeLayout = (LinearLayout) rootView.findViewById(R.id.news_post_like_layout);
        postLikeImage = (ImageView) rootView.findViewById(R.id.news_post_like_image);
        postLikeCount = (RobotoTextView) rootView.findViewById(R.id.news_post_like_count);
        postCommentLayout = (LinearLayout) rootView.findViewById(R.id.news_post_comment_layout);
        postCommentCount = (RobotoTextView) rootView.findViewById(R.id.news_post_comment_count);
        postShareLayout = (LinearLayout) rootView.findViewById(R.id.news_post_share_layout);
        postShareImage = (ImageView) rootView.findViewById(R.id.news_post_share_image);
        postShareCount = (RobotoTextView) rootView.findViewById(R.id.news_post_share_count);
        photoViewerImage = (ImageView) rootView.findViewById(R.id.photo_viewer_info_image);
        topLayout = (RelativeLayout) rootView.findViewById(R.id.photo_viewer_top_layout);

        ((AppCompatActivity) getActivity()).setSupportActionBar(getToolbar());
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

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
        return rootView;
    }

    @Override
    protected void initContent(Bundle bundle) {
        imagePos = bundle.getInt(IMAGE_POSITION, 0);
       // sourceId = bundle.getInt(IMAGE_SOURCE, 0);
        date = bundle.getInt(IMAGE_DATE, 0);
        photos = (Photos) bundle.getSerializable(IMAGE_ARRAY);
        type = EnumUtils.deserialize(FilterType.class).from(bundle, FilterType.POST);

        latch = new CountDownLatch(1);
    }

    private void createViewPager(){
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        actionBar.setTitle((imagePos + 1) + " " + getResources().getString(R.string.main_string_of) + " " + photos.getCount());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PhotoViewerAdapter adapter = new PhotoViewerAdapter(((AppCompatActivity) getActivity()).getSupportFragmentManager(), photos, new View.OnClickListener() {
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
    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    private VKQuery<Photos> createQuery() {
        VKQuery query = null;
        VKQueryBuilder<Photos> builder = new VKQueryBuilder<>(getUser());
        builder.setVKQueryType(VKQueryType.PHOTO);
        builder.setVKMethod(VKQuerySubMethod.GET);
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
            queryParamBuilder.append(",");
        }
        queryParamBuilder.deleteCharAt(queryParamBuilder.length()-1);

        VKQuery<Photos> query = null;
        VKQueryBuilder<Photos> builder = new VKQueryBuilder<>(getUser());
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

    private void loadPhotoData(final PhotosInfo currentPhoto){
        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(getActivity(), R.drawable.news_post_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        postLikeImage.setImageBitmap(bitmap);
                        LikesInfo likes = currentPhoto.getLikes();

                        if (likes != null) {
                            postLikeCount.setText(String.valueOf(likes.getCount()));
                            if (likes.getUserLikes())
                                ViewCompat.setAlpha(postLikeImage, 0.5f);
                            else ViewCompat.setAlpha(postLikeImage, 1f);
                        }
                    }
                });
            }
        });

        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(getContext(), R.drawable.news_post_comment, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

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
                    }
                });
            }
        });
        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(getContext(), R.drawable.news_share_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        postShareImage.setImageBitmap(bitmap);
                    }
                });
            }
        });

        VKMainExecutor.executeRunnable(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    Bitmap bitmap = BitmapUtils.appyColorFilterForResource(getContext(), R.drawable.photo_viewer_info_white, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                    @Override
                    public void run() {
                        photoViewerImage.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

}
