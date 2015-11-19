package ru.diaproject.vkplus.news.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.BaseRequestOptions;


import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.ParentActivityNoTitle;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.photoviewer.PhotoViewerActivity;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.vkcore.user.VKUser;


public class PhotoViewContainer extends LinearLayout {

    private Photos photos;
    private Integer ownerId;
    private Integer date;
    private VKUser user;

    @Bind(R.id.one_or_two_vertical_layout)
    public LinearLayout oneOrTwoVerticalLayout;

    @Bind(R.id.first_im_view_vertical)
    public ImageView firstImViewVertical;

    @Bind(R.id.second_im_view_vertical)
    public ImageView secondImViewVertical;

    @Bind(R.id.one_or_two_horizontal_layout)
    public LinearLayout oneOrTwoHorizontalLayout;

    @Bind(R.id.first_im_view_horizontal)
    public ImageView firstImViewHorizontal;

    @Bind(R.id.second_im_view_horizontal)
    public ImageView secondImViewHorizontal;

    @Bind(R.id.three_or_four_layout)
    public FrameLayout threeOrFourLayout;

    @Bind(R.id.sub_layout_34_horizontal)
    public LinearLayout subLyout34Horizontal;

    @Bind(R.id.main_34_im_view)
    public ImageView main34view;

    @Bind(R.id.first_34_im_view_horizontal)
    public ImageView first34viewHorizontal;

    @Bind(R.id.second_34_im_view_horizontal)
    public ImageView second34viewHorzintal;

    @Bind(R.id.third_34_im_view_horizontal)
    public ImageView third34viewHorzintal;

    @Bind(R.id.fourth_34_im_view_horizontal)
    public ImageView fourth34viewHorizontal;

    @Bind(R.id.sub_layout_34_vertical)
    public LinearLayout subLyout34Vertical;

    @Bind(R.id.first_34_im_view_vertical)
    public ImageView first34viewVertical;

    @Bind(R.id.second_34_im_view_vertical)
    public ImageView second34viewVertical;

    @Bind(R.id.third_34_im_view_vertical)
    public ImageView third34viewVertical;

    @Bind(R.id.fourth_34_im_view_vertical)
    public ImageView fourth34viewVertical;

    @Bind(R.id.six_layout)
    public LinearLayout sixLayout;

    @Bind(R.id.main_six_im_view)
    public ImageView mainSixImView;

    @Bind(R.id.first_six_im_view)
    public ImageView firstSixImView;

    @Bind(R.id.second_six_im_view)
    public ImageView secondSixImView;

    @Bind(R.id.third_six_im_view)
    public ImageView thirdSixImView;

    @Bind(R.id.fourth_six_im_view)
    public ImageView fourthSixImView;

    @Bind(R.id.five_six_im_view)
    public ImageView fiveSixImView;

    @Bind(R.id.seven_layout_horizontal)
    public LinearLayout sevenLayoutHorizontal;

    @Bind(R.id.main_seven_im_view_horizontal)
    public ImageView mainSevenImViewHorizontal;

    @Bind(R.id.first_seven_im_view_horizontal)
    public ImageView firstSevenImViewHorizontal;

    @Bind(R.id.second_seven_im_view_horizontal)
    public ImageView secondSevenImViewHorizontal;

    @Bind(R.id.third_seven_im_view_horizontal)
    public ImageView thirdSevenImViewHorizontal;

    @Bind(R.id.fourth_seven_im_view_horizontal)
    public ImageView fourthSevenImViewHorizontal;

    @Bind(R.id.five_seven_im_view_horizontal)
    public ImageView fiveSevenImViewHorizontal;

    @Bind(R.id.six_seven_im_view_horizontal)
    public ImageView sixSevenImViewHorizontal;

    @Bind(R.id.seven_layout_vertical)
    public LinearLayout sevenLayoutVertical;

    @Bind(R.id.main_seven_im_view_vertical)
    public ImageView mainSevenImViewVertical;

    @Bind(R.id.first_seven_im_view_vertical)
    public ImageView firstSevenImViewVertical;

    @Bind(R.id.second_seven_im_view_vertical)
    public ImageView secondSevenImViewVertical;

    @Bind(R.id.third_seven_im_view_vertical)
    public ImageView thirdSevenImViewVertical;

    @Bind(R.id.fourth_seven_im_view_vertical)
    public ImageView fourthSevenImViewVertical;

    @Bind(R.id.five_seven_im_view_vertical)
    public ImageView fiveSevenImViewVertical;

    @Bind(R.id.six_seven_im_view_vertical)
    public ImageView sixSevenImViewVertical;

    BaseRequestOptions pictureOptions;
    private NewsPagerCardFragment fragment;

    public PhotoViewContainer(Context context) {
        super(context);
        init();
    }

    public PhotoViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.news_photos_layout, null);
        ButterKnife.bind(this, view);

        addView(view);
        pictureOptions = new BaseRequestOptions() {
        };

        pictureOptions.format(DecodeFormat.PREFER_RGB_565);
        pictureOptions.placeholder(R.drawable.picture_placeholder);
        pictureOptions.error(R.drawable.picture_placeholder);
    }

    public void setData(Photos photos, Integer ownerId, Integer date, VKUser user){
        this.ownerId = ownerId;
        this.date = date;
        this.user = user;
        this.photos = photos;

        Integer size = photos.getPhotos().size();
        if (size.equals(1)){
            oneOrTwoHorizontalLayout.setVisibility(View.GONE);
            oneOrTwoVerticalLayout.setVisibility(View.VISIBLE);
            secondImViewVertical.setVisibility(View.GONE);
            threeOrFourLayout.setVisibility(View.GONE);
            sixLayout.setVisibility(View.GONE);
            sevenLayoutHorizontal.setVisibility(View.GONE);
            sevenLayoutVertical.setVisibility(View.GONE);

            prepareImageView(firstImViewVertical, photos.getPhotos().get(0).getPhoto604(),0);

        }else if (size.equals(2)){
            if (isHorizontal(photos)){
                oneOrTwoVerticalLayout.setVisibility(View.GONE);
                oneOrTwoHorizontalLayout.setVisibility(View.VISIBLE);
                threeOrFourLayout.setVisibility(View.GONE);
                sixLayout.setVisibility(View.GONE);
                sevenLayoutHorizontal.setVisibility(View.GONE);
                sevenLayoutVertical.setVisibility(View.GONE);

                prepareImageView(firstImViewHorizontal, photos.getPhotos().get(0).getPhoto604(),0);
                prepareImageView(secondImViewHorizontal, photos.getPhotos().get(1).getPhoto604(), 1);
            }else{
                oneOrTwoVerticalLayout.setVisibility(View.VISIBLE);
                secondImViewVertical.setVisibility(View.VISIBLE);
                oneOrTwoHorizontalLayout.setVisibility(View.GONE);
                threeOrFourLayout.setVisibility(View.GONE);
                sixLayout.setVisibility(View.GONE);
                sevenLayoutHorizontal.setVisibility(View.GONE);
                sevenLayoutVertical.setVisibility(View.GONE);

                prepareImageView(firstImViewVertical, photos.getPhotos().get(0).getPhoto604(), 0);
                prepareImageView(secondImViewVertical, photos.getPhotos().get(1).getPhoto604(), 1);
            }
        }else if(size<= 5) {
            oneOrTwoVerticalLayout.setVisibility(View.GONE);
            oneOrTwoHorizontalLayout.setVisibility(View.GONE);
            threeOrFourLayout.setVisibility(View.VISIBLE);
            sixLayout.setVisibility(View.GONE);
            sevenLayoutHorizontal.setVisibility(View.GONE);
            sevenLayoutVertical.setVisibility(View.GONE);

            prepareImageView(main34view, photos.getPhotos().get(0).getPhoto604(),0);
            if (!isHorizontal(photos)){
                subLyout34Vertical.setVisibility(View.GONE);
                subLyout34Horizontal.setVisibility(View.VISIBLE);
                prepareImageView(first34viewHorizontal, photos.getPhotos().get(1).getPhoto130(),1);
                if (size.equals(3)) {
                    second34viewHorzintal.setVisibility(View.GONE);
                    third34viewHorzintal.setVisibility(View.GONE);

                    prepareImageView(fourth34viewHorizontal, photos.getPhotos().get(2).getPhoto130(),2);
                }else if (size.equals(4)) {
                    second34viewHorzintal.setVisibility(View.VISIBLE);
                    third34viewHorzintal.setVisibility(View.GONE);

                    prepareImageView(second34viewHorzintal, photos.getPhotos().get(2).getPhoto130(),2);
                    prepareImageView(fourth34viewHorizontal, photos.getPhotos().get(3).getPhoto130(),3);
                } else{
                    second34viewHorzintal.setVisibility(View.VISIBLE);
                    third34viewHorzintal.setVisibility(View.VISIBLE);

                    prepareImageView(second34viewHorzintal, photos.getPhotos().get(2).getPhoto130(),2);
                    prepareImageView(third34viewHorzintal, photos.getPhotos().get(3).getPhoto130(),3);
                    prepareImageView(fourth34viewHorizontal, photos.getPhotos().get(4).getPhoto130(),4);
                }
            } else{
                subLyout34Horizontal.setVisibility(View.GONE);
                subLyout34Vertical.setVisibility(View.VISIBLE);
                prepareImageView(first34viewVertical, photos.getPhotos().get(1).getPhoto130(),1);

                if (size.equals(3)) {
                    second34viewVertical.setVisibility(View.GONE);
                    third34viewVertical.setVisibility(View.GONE);

                    prepareImageView(fourth34viewVertical, photos.getPhotos().get(2).getPhoto130(),2);
                }else if (size.equals(4)) {
                    second34viewVertical.setVisibility(View.VISIBLE);
                    third34viewVertical.setVisibility(View.GONE);

                    prepareImageView(second34viewVertical, photos.getPhotos().get(2).getPhoto130(),2);
                    prepareImageView(fourth34viewVertical, photos.getPhotos().get(3).getPhoto130(),3);
                } else{
                    second34viewVertical.setVisibility(View.VISIBLE);
                    third34viewVertical.setVisibility(View.VISIBLE);

                    prepareImageView(second34viewVertical, photos.getPhotos().get(2).getPhoto130(),2);
                    prepareImageView(third34viewVertical, photos.getPhotos().get(3).getPhoto130(),3);
                    prepareImageView(fourth34viewVertical, photos.getPhotos().get(4).getPhoto130(),4);
                }
            }
        }
        else if(size.equals(6)){
            oneOrTwoVerticalLayout.setVisibility(View.GONE);
            oneOrTwoHorizontalLayout.setVisibility(View.GONE);
            threeOrFourLayout.setVisibility(View.GONE);
            sixLayout.setVisibility(View.VISIBLE);
            sevenLayoutHorizontal.setVisibility(View.GONE);
            sevenLayoutVertical.setVisibility(View.GONE);

            prepareImageView(mainSixImView, photos.getPhotos().get(0).getPhoto604(),0);
            prepareImageView(firstSixImView, photos.getPhotos().get(1).getPhoto130(),1);
            prepareImageView(secondSixImView, photos.getPhotos().get(2).getPhoto130(),2);
            prepareImageView(thirdSixImView, photos.getPhotos().get(3).getPhoto130(),3);
            prepareImageView(fourthSixImView, photos.getPhotos().get(4).getPhoto130(),4);
            prepareImageView(fiveSixImView, photos.getPhotos().get(5).getPhoto130(),5);
        }
        else {
            oneOrTwoVerticalLayout.setVisibility(View.GONE);
            oneOrTwoHorizontalLayout.setVisibility(View.GONE);
            threeOrFourLayout.setVisibility(View.GONE);
            sixLayout.setVisibility(View.GONE);

            if (isHorizontal(photos)) {
                sevenLayoutHorizontal.setVisibility(View.VISIBLE);
                sevenLayoutVertical.setVisibility(View.GONE);

                prepareImageView(mainSevenImViewHorizontal, photos.getPhotos().get(0).getPhoto604(),0);
                prepareImageView(firstSevenImViewHorizontal, photos.getPhotos().get(1).getPhoto130(),1);
                prepareImageView(secondSevenImViewHorizontal, photos.getPhotos().get(2).getPhoto130(),2);
                prepareImageView(thirdSevenImViewHorizontal, photos.getPhotos().get(3).getPhoto130(),3);
                prepareImageView(fourthSevenImViewHorizontal, photos.getPhotos().get(4).getPhoto130(),4);
                prepareImageView(fiveSevenImViewHorizontal, photos.getPhotos().get(5).getPhoto130(),5);
                prepareImageView(sixSevenImViewHorizontal, photos.getPhotos().get(6).getPhoto130(), 6);
            }else{
                sevenLayoutHorizontal.setVisibility(View.GONE);
                sevenLayoutVertical.setVisibility(View.VISIBLE);
                prepareImageView(mainSevenImViewVertical, photos.getPhotos().get(0).getPhoto604(),0);
                prepareImageView(firstSevenImViewVertical, photos.getPhotos().get(1).getPhoto130(),1);
                prepareImageView(secondSevenImViewVertical, photos.getPhotos().get(2).getPhoto130(),2);
                prepareImageView(thirdSevenImViewVertical, photos.getPhotos().get(3).getPhoto130(),3);
                prepareImageView(fourthSevenImViewVertical, photos.getPhotos().get(4).getPhoto130(),4);
                prepareImageView(fiveSevenImViewVertical, photos.getPhotos().get(5).getPhoto130(),6);
                prepareImageView(sixSevenImViewVertical, photos.getPhotos().get(6).getPhoto130(),7);
            }
        }
    }
    public boolean isHorizontal(Photos item){
        int height = item.getPhotos().get(0).getHeight();
        int width = item.getPhotos().get(0).getWidth();
        return width<= height;
    }

    public void clear(final NewsPagerCardFragment newsPagerCardFragment) {
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                Glide.with(newsPagerCardFragment).clear(firstImViewVertical);
                Glide.with(newsPagerCardFragment).clear(secondImViewVertical);
                Glide.with(newsPagerCardFragment).clear(firstImViewHorizontal);
                Glide.with(newsPagerCardFragment).clear(secondImViewHorizontal);

                Glide.with(newsPagerCardFragment).clear(main34view);
                Glide.with(newsPagerCardFragment).clear(first34viewHorizontal);
                Glide.with(newsPagerCardFragment).clear(second34viewHorzintal);
                Glide.with(newsPagerCardFragment).clear(third34viewHorzintal);
                Glide.with(newsPagerCardFragment).clear(fourth34viewHorizontal);

                Glide.with(newsPagerCardFragment).clear(first34viewVertical);
                Glide.with(newsPagerCardFragment).clear(second34viewVertical);
                Glide.with(newsPagerCardFragment).clear(third34viewVertical);
                Glide.with(newsPagerCardFragment).clear(fourth34viewVertical);

                Glide.with(newsPagerCardFragment).clear(mainSixImView);
                Glide.with(newsPagerCardFragment).clear(firstSixImView);
                Glide.with(newsPagerCardFragment).clear(secondSixImView);
                Glide.with(newsPagerCardFragment).clear(thirdSixImView);
                Glide.with(newsPagerCardFragment).clear(fourthSixImView);
                Glide.with(newsPagerCardFragment).clear(fiveSixImView);

                Glide.with(newsPagerCardFragment).clear(mainSevenImViewHorizontal);
                Glide.with(newsPagerCardFragment).clear(firstSevenImViewHorizontal);
                Glide.with(newsPagerCardFragment).clear(secondSevenImViewHorizontal);
                Glide.with(newsPagerCardFragment).clear(thirdSevenImViewHorizontal);
                Glide.with(newsPagerCardFragment).clear(fourthSevenImViewHorizontal);
                Glide.with(newsPagerCardFragment).clear(fiveSevenImViewHorizontal);
                Glide.with(newsPagerCardFragment).clear(sixSevenImViewHorizontal);

                Glide.with(newsPagerCardFragment).clear(mainSevenImViewVertical);
                Glide.with(newsPagerCardFragment).clear(firstSevenImViewVertical);
                Glide.with(newsPagerCardFragment).clear(secondSevenImViewVertical);
                Glide.with(newsPagerCardFragment).clear(thirdSevenImViewVertical);
                Glide.with(newsPagerCardFragment).clear(fourthSevenImViewVertical);
                Glide.with(newsPagerCardFragment).clear(fiveSevenImViewVertical);
                Glide.with(newsPagerCardFragment).clear(sixSevenImViewVertical);
            }
        });

    }

    public void prepareImageView(final ImageView view, final String url, final int position){
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getContext())
                .load(url)
                .apply(pictureOptions)
                .into(view);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PhotoViewerActivity.class);
                intent.putExtra(PhotoViewerActivity.IMAGE_SOURCE, ownerId);
                intent.putExtra(PhotoViewerActivity.IMAGE_POSITION, position);
                intent.putExtra(PhotoViewerActivity.IMAGE_DATE, date);
                intent.putExtra(ParentActivityNoTitle.VK_USER_ARG, user);
                getContext().startActivity(intent);
            }
        });
    }
}
