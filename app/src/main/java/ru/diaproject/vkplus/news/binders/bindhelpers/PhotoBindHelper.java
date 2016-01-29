package ru.diaproject.vkplus.news.binders.bindhelpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.utils.DataConstants;
import ru.diaproject.vkplus.core.utils.EnumUtils;
import ru.diaproject.vkplus.news.model.baseitems.FilterType;
import ru.diaproject.vkplus.news.model.baseitems.IDataMainItem;
import ru.diaproject.vkplus.news.model.baseitems.PostType;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.PhotosInfo;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.utils.PhotoConstants;
import ru.diaproject.vkplus.news.viewholders.DataPhotosViewHolder;
import ru.diaproject.vkplus.photoviewer.PhotoViewerActivity;

public class PhotoBindHelper {

    private Context context;
    private int photoContainerWidth;
    private int TWO_IMAGE_MIDDLE_OFFSET;

    public PhotoBindHelper(Context context, int defaultItemWidth, int offset) {
        this.context = context;
        photoContainerWidth = defaultItemWidth;
        TWO_IMAGE_MIDDLE_OFFSET = offset;
    }

    private void setPhotos(final Photos photos, final DataPhotosViewHolder holder, final Integer ownerId, final Integer date, final FilterType postType){
        PhotosInfo mainPhoto = photos.getPhotos().get(0);
        int size = photos.getPhotos().size();

        if (size == 1) {
            holder.photoSubContainer.setVisibility(View.GONE);
            calculateOneImageView(mainPhoto, holder.mainImage);
            Glide.with(context).load(mainPhoto.getPhoto604()).into(holder.mainImage);

        } else if (size == 2) {
            holder.photoSubContainer.setVisibility(View.VISIBLE);
            holder.firstImage.setVisibility(View.VISIBLE);
            holder.secondImage.setVisibility(View.GONE);
            holder.thirdImage.setVisibility(View.GONE);
            holder.fourthImage.setVisibility(View.GONE);

            PhotosInfo firstPhoto = photos.getPhotos().get(1);
            if (!(isRectangularHorizontal(firstPhoto) && isRectangularHorizontal(mainPhoto)))
                calculateTwoHorizontalImageViews(mainPhoto, firstPhoto, holder.mainImage, holder.photoSubContainer, holder.firstImage);
            else
                calculateTwoVerticalImageViews(mainPhoto, firstPhoto, holder.mainImage,holder. photoSubContainer, holder.firstImage);

            Glide.with(context).load(mainPhoto.getPhoto604()).into(holder.mainImage);
            Glide.with(context).load(firstPhoto.getPhoto604()).into(holder.firstImage);

        } else if (size == 3) {
            holder.photoSubContainer.setVisibility(View.VISIBLE);
            holder.firstImage.setVisibility(View.VISIBLE);
            holder.secondImage.setVisibility(View.VISIBLE);
            holder.thirdImage.setVisibility(View.GONE);
            holder.fourthImage.setVisibility(View.GONE);

            PhotosInfo firstPhoto = photos.getPhotos().get(1);
            PhotosInfo secondPhoto = photos.getPhotos().get(2);

            if (isHorizontal(mainPhoto))
                calculateBottomSubViews(mainPhoto, firstPhoto, secondPhoto, holder.mainImage, holder.photoSubContainer, holder.firstImage, holder.secondImage);
            else
                calculateRightSubViews(mainPhoto, firstPhoto, secondPhoto, holder.mainImage, holder.photoSubContainer, holder.firstImage, holder.secondImage);

            Glide.with(context).load(mainPhoto.getPhoto604()).into(holder.mainImage);
            Glide.with(context).load(firstPhoto.getPhoto604()).into(holder.firstImage);
            Glide.with(context).load(secondPhoto.getPhoto604()).into(holder.secondImage);
        } else if (size == 4) {
            holder.photoSubContainer.setVisibility(View.VISIBLE);
            holder.firstImage.setVisibility(View.VISIBLE);
            holder.secondImage.setVisibility(View.VISIBLE);
            holder.thirdImage.setVisibility(View.VISIBLE);
            holder.fourthImage.setVisibility(View.GONE);

            PhotosInfo firstPhoto = photos.getPhotos().get(1);
            PhotosInfo secondPhoto = photos.getPhotos().get(2);
            PhotosInfo thirdPhoto = photos.getPhotos().get(3);

            if (isHorizontal(mainPhoto))
                calculateBottomSubThreeViews(mainPhoto, firstPhoto, secondPhoto, thirdPhoto, holder.mainImage,
                        holder.photoSubContainer, holder.firstImage, holder.secondImage, holder.thirdImage);
            else
                calculateRightSubThreeViews(mainPhoto, firstPhoto, secondPhoto, thirdPhoto, holder.mainImage,
                        holder.photoSubContainer, holder.firstImage, holder.secondImage, holder.thirdImage);

            Glide.with(context).load(mainPhoto.getPhoto604()).into(holder.mainImage);
            Glide.with(context).load(firstPhoto.getPhoto604()).into(holder.firstImage);
            Glide.with(context).load(secondPhoto.getPhoto604()).into(holder.secondImage);
            Glide.with(context).load(thirdPhoto.getPhoto604()).into(holder.thirdImage);
        } else if (size >= 5) {
            holder.photoSubContainer.setVisibility(View.VISIBLE);
            holder.firstImage.setVisibility(View.VISIBLE);
            holder.secondImage.setVisibility(View.VISIBLE);
            holder.thirdImage.setVisibility(View.VISIBLE);
            holder.fourthImage.setVisibility(View.VISIBLE);

            PhotosInfo firstPhoto = photos.getPhotos().get(1);
            PhotosInfo secondPhoto = photos.getPhotos().get(2);
            PhotosInfo thirdPhoto = photos.getPhotos().get(3);
            PhotosInfo fourthPhoto = photos.getPhotos().get(4);

            if (isHorizontal(mainPhoto))
                calculateBottomSubFourViews(mainPhoto, firstPhoto, secondPhoto, thirdPhoto, fourthPhoto,
                        holder.mainImage, holder.photoSubContainer, holder.firstImage, holder.secondImage, holder.thirdImage, holder.fourthImage);
            else
                calculateRightSubFourViews(mainPhoto, firstPhoto, secondPhoto, thirdPhoto, fourthPhoto,
                        holder.mainImage, holder.photoSubContainer, holder.firstImage, holder.secondImage, holder.thirdImage, holder.fourthImage);

            Glide.with(context).load(mainPhoto.getPhoto604()).into(holder.mainImage);
            Glide.with(context).load(firstPhoto.getPhoto604()).into(holder.firstImage);
            Glide.with(context).load(secondPhoto.getPhoto604()).into(holder.secondImage);
            Glide.with(context).load(thirdPhoto.getPhoto604()).into(holder.thirdImage);
            Glide.with(context).load(fourthPhoto.getPhoto604()).into(holder.fourthImage);
        }

        holder.mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int [] positions = new int[2];
                holder.mainImage.getLocationOnScreen(positions);

                Rect r = new Rect();
                holder.mainImage.getLocalVisibleRect(r);

                Intent intent = new Intent(context, PhotoViewerActivity.class);
                intent.putExtra(PhotoConstants.IMAGE_ARRAY, photos);
                intent.putExtra(PhotoConstants.IMAGE_POSITION, 0);
                intent.putExtra(DataConstants.OWNER_ID, ownerId);
                intent.putExtra(DataConstants.DATE, date);
                intent.putExtra(PhotoConstants.IMAGE_X, positions[0]);
                intent.putExtra(PhotoConstants.IMAGE_Y, positions[1]);
                intent.putExtra(PhotoConstants.IMAGE_WIDTH, holder.mainImage.getWidth());
                intent.putExtra(PhotoConstants.IMAGE_HEIGHT, holder.mainImage.getHeight());
                intent.putExtra(PhotoConstants.IMAGE_VISIBLE_HEIGHT_START, r.top);
                intent.putExtra(PhotoConstants.IMAGE_VISIBLE_HEIGHT_END, r.bottom);
                EnumUtils.serialize(postType).to(intent);
                context.startActivity(intent);
            }
        });

        holder.firstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewerActivity.class);
                intent.putExtra(PhotoConstants.IMAGE_ARRAY, photos);
                intent.putExtra(PhotoConstants.IMAGE_POSITION, 1);
                intent.putExtra(PhotoConstants.IMAGE_X, holder.firstImage.getX());
                intent.putExtra(PhotoConstants.IMAGE_Y, holder.firstImage.getY());
                intent.putExtra(PhotoConstants.IMAGE_WIDTH, holder.firstImage.getWidth());
                intent.putExtra(PhotoConstants.IMAGE_HEIGHT, holder.firstImage.getHeight());
                context.startActivity(intent);
            }
        });

        holder.secondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewerActivity.class);
                intent.putExtra(PhotoConstants.IMAGE_ARRAY, photos);
                intent.putExtra(PhotoConstants.IMAGE_POSITION, 2);
                intent.putExtra(PhotoConstants.IMAGE_X, holder.secondImage.getX());
                intent.putExtra(PhotoConstants.IMAGE_Y, holder.secondImage.getY());
                intent.putExtra(PhotoConstants.IMAGE_WIDTH, holder.secondImage.getWidth());
                intent.putExtra(PhotoConstants.IMAGE_HEIGHT, holder.secondImage.getHeight());
                context.startActivity(intent);
            }
        });

        holder.thirdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewerActivity.class);
                intent.putExtra(PhotoConstants.IMAGE_ARRAY, photos);
                intent.putExtra(PhotoConstants.IMAGE_POSITION, 3);
                intent.putExtra(PhotoConstants.IMAGE_X, holder.thirdImage.getX());
                intent.putExtra(PhotoConstants.IMAGE_Y, holder.thirdImage.getY());
                intent.putExtra(PhotoConstants.IMAGE_WIDTH, holder.thirdImage.getWidth());
                intent.putExtra(PhotoConstants.IMAGE_HEIGHT, holder.thirdImage.getHeight());
                context.startActivity(intent);
            }
        });

        holder.fourthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewerActivity.class);
                intent.putExtra(PhotoConstants.IMAGE_ARRAY, photos);
                intent.putExtra(PhotoConstants.IMAGE_POSITION, 3);
                intent.putExtra(PhotoConstants.IMAGE_X, holder.fourthImage.getX());
                intent.putExtra(PhotoConstants.IMAGE_Y, holder.fourthImage.getY());
                intent.putExtra(PhotoConstants.IMAGE_WIDTH, holder.fourthImage.getWidth());
                intent.putExtra(PhotoConstants.IMAGE_HEIGHT, holder.fourthImage.getHeight());
                context.startActivity(intent);
            }
        });
    }
    public void setPhotos(final IDataMainItem entity, final DataPhotosViewHolder holder) {
        setPhotos(entity.getAttachmentPhotos(), holder, entity.getOwnerId(), entity.getDate(), entity.getType());
    }

    public void setPhotos(final CopyHistoryInfo entity, final DataPhotosViewHolder holder) {
        setPhotos(entity.getAttachmentPhotos(), holder, entity.getOwnerId(), entity.getDate(), entity.getPostType());
    }


    private void calculateRightSubFourViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, PhotosInfo secondPhoto,
                                            PhotosInfo thirdPhoto, PhotosInfo fourthPhoto, ImageView mainImage, RelativeLayout photoSubContainer,
                                            ImageView firstImage, ImageView secondImage, ImageView thirdImage, ImageView fourthImage) {
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();

        int newMainHeight = mainHeight;
        int newMainWidth = mainWidth;

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        int secondWidth = secondPhoto.getWidth();
        int secondHeight = secondPhoto.getHeight();

        int newSecondWidth = secondWidth;
        int newSecondHeight = secondHeight;

        int thirdWidth = thirdPhoto.getWidth();
        int thirdHeight = thirdPhoto.getHeight();

        int newThirdWidth = thirdWidth;
        int newThirdHeight = thirdHeight;

        int fourthWidth = fourthPhoto.getWidth();
        int fourthHeight = fourthPhoto.getHeight();

        int newFourthWidth = fourthWidth;
        int newFourthHeight = fourthHeight;

        float resizeCoeff;

        if (firstWidth >= secondWidth) {
            resizeCoeff = (float) firstWidth / secondWidth;
            newFirstHeight = (int) (newFirstHeight / resizeCoeff);
            newFirstWidth = (int) (newFirstWidth / resizeCoeff);
        } else {
            resizeCoeff = (float) secondWidth / firstWidth;
            newSecondHeight = (int) (newSecondHeight / resizeCoeff);
            newSecondWidth = (int) (newSecondWidth / resizeCoeff);
        }
        resizeCoeff = (float) thirdWidth / firstWidth;
        newThirdWidth = (int) (newThirdWidth / resizeCoeff);
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);

        newFourthHeight = (int) (newFourthHeight / resizeCoeff);
        newFourthWidth = (int) (newFourthWidth / resizeCoeff);

        int subItemsTotalHeight = newFirstHeight + newSecondHeight + newThirdHeight + newFourthHeight;
        resizeCoeff = (float) newMainHeight / (subItemsTotalHeight);

        newMainHeight = (int) (newMainHeight / resizeCoeff);
        newMainWidth = (int) (newMainWidth / resizeCoeff);

        int totalWidth = newMainWidth + newFirstWidth;
        resizeCoeff = (float) totalWidth / (photoContainerWidth - TWO_IMAGE_MIDDLE_OFFSET);

        newMainHeight = (int) (newMainHeight / resizeCoeff);
        newMainWidth = (int) (newMainWidth / resizeCoeff);

        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = newFirstWidth;

        newThirdWidth = newFirstWidth;
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);

        newFourthHeight = (int) (newFourthHeight / resizeCoeff);
        newFourthWidth = newFirstWidth;

        resizeCoeff = (float) mainHeight / (mainHeight - 3 * TWO_IMAGE_MIDDLE_OFFSET);

        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = newFirstWidth;

        newThirdWidth = newFirstWidth;
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);

        newFourthHeight = (int) (newFourthHeight / resizeCoeff);
        newFourthWidth = newFirstWidth;

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(newFirstWidth,
                newMainHeight);
        subContainerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        subContainerParams.setMargins(TWO_IMAGE_MIDDLE_OFFSET, 0, TWO_IMAGE_MIDDLE_OFFSET, 0);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);

        RelativeLayout.LayoutParams secondParams = new RelativeLayout.LayoutParams(newSecondWidth, newSecondHeight);
        secondParams.addRule(RelativeLayout.BELOW, R.id.news_photos_first_image);
        secondParams.setMargins(0, TWO_IMAGE_MIDDLE_OFFSET, 0, 0);
        secondImage.setLayoutParams(secondParams);

        RelativeLayout.LayoutParams thirdParams = new RelativeLayout.LayoutParams(newThirdWidth, newThirdHeight);
        thirdParams.addRule(RelativeLayout.BELOW, R.id.news_photos_second_image);
        thirdParams.setMargins(0, TWO_IMAGE_MIDDLE_OFFSET, 0, 0);
        thirdImage.setLayoutParams(thirdParams);

        RelativeLayout.LayoutParams fourthParams = new RelativeLayout.LayoutParams(newFourthWidth, newFourthHeight);
        fourthParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        fourthImage.setLayoutParams(fourthParams);
    }

    private void calculateBottomSubFourViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, PhotosInfo secondPhoto,
                                             PhotosInfo thirdPhoto, PhotosInfo fourthPhoto, ImageView mainImage, RelativeLayout photoSubContainer,
                                             ImageView firstImage, ImageView secondImage, ImageView thirdImage, ImageView fourthImage) {
        //Prepare main view
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();
        float resizeCoeff = (float) mainWidth / mainHeight;

        int newMainWidth = photoContainerWidth;
        int newMainHeight = (int) (newMainWidth / resizeCoeff);

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        // prepare sub items

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int secondWidth = secondPhoto.getWidth();
        int secondHeight = secondPhoto.getHeight();

        int thirdWidth = thirdPhoto.getWidth();
        int thirdHeight = thirdPhoto.getHeight();

        int fourthWidth = fourthPhoto.getWidth();
        int fourthHeight = fourthPhoto.getHeight();

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        int newSecondWidth = secondWidth;
        int newSecondHeight = secondHeight;

        int newThirdWidth = thirdWidth;
        int newThirdHeight = thirdHeight;

        int newFourthWidth = fourthWidth;
        int newFourthHeight = fourthHeight;

        if (firstHeight >= secondHeight) {
            resizeCoeff = (float) firstHeight / secondHeight;
            newFirstHeight = (int) (newFirstHeight / resizeCoeff);
            newFirstWidth = (int) (newFirstWidth / resizeCoeff);
        } else {
            resizeCoeff = (float) secondHeight / firstHeight;
            newSecondHeight = (int) (newSecondHeight / resizeCoeff);
            newSecondWidth = (int) (newSecondWidth / resizeCoeff);
        }

        resizeCoeff = (float) newThirdHeight / newFirstHeight;
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);
        newThirdWidth = (int) (newThirdWidth / resizeCoeff);

        newFourthHeight = (int) (newFourthHeight / resizeCoeff);
        newFourthWidth = (int) (newFourthWidth / resizeCoeff);

        float totalWidth = photoContainerWidth - 3 * TWO_IMAGE_MIDDLE_OFFSET;
        resizeCoeff = (newFirstWidth + newSecondWidth + newThirdWidth + newFourthWidth) / totalWidth;
        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = (int) (newSecondWidth / resizeCoeff);

        newThirdHeight = (int) (newThirdHeight / resizeCoeff);
        newThirdWidth = (int) (newThirdWidth / resizeCoeff);

        newFourthHeight = (int) (newFourthHeight / resizeCoeff);
        newFourthWidth = (int) (newFourthWidth / resizeCoeff);

        //prepare subcontainer: width all parent. SubContainer leaves below main imageView
        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(photoContainerWidth, newFirstHeight);
        subContainerParams.addRule(RelativeLayout.BELOW, R.id.news_photos_main_image);
        subContainerParams.setMargins(0, TWO_IMAGE_MIDDLE_OFFSET, 0, 0);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);

        RelativeLayout.LayoutParams secondParams = new RelativeLayout.LayoutParams(newSecondWidth, newSecondHeight);
        secondParams.addRule(RelativeLayout.RIGHT_OF, R.id.news_photos_first_image);
        secondParams.setMargins(TWO_IMAGE_MIDDLE_OFFSET, 0, 0, 0);
        secondImage.setLayoutParams(secondParams);

        RelativeLayout.LayoutParams thirdParams = new RelativeLayout.LayoutParams(newThirdWidth, newThirdHeight);
        thirdParams.addRule(RelativeLayout.RIGHT_OF, R.id.news_photos_second_image);
        thirdParams.setMargins(TWO_IMAGE_MIDDLE_OFFSET, 0, 0, 0);
        thirdImage.setLayoutParams(thirdParams);

        RelativeLayout.LayoutParams fourthParams = new RelativeLayout.LayoutParams(newFourthWidth, newFourthHeight);
        fourthParams.addRule(RelativeLayout.RIGHT_OF, R.id.news_photos_third_image);
        fourthParams.setMargins(TWO_IMAGE_MIDDLE_OFFSET, 0, 0, 0);
        fourthImage.setLayoutParams(fourthParams);
    }

    private void calculateRightSubThreeViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, PhotosInfo secondPhoto,
                                             PhotosInfo thirdPhoto, ImageView mainImage, RelativeLayout photoSubContainer,
                                             ImageView firstImage, ImageView secondImage, ImageView thirdImage) {
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();

        int newMainHeight = mainHeight;
        int newMainWidth = mainWidth;

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        int secondWidth = secondPhoto.getWidth();
        int secondHeight = secondPhoto.getHeight();

        int newSecondWidth = secondWidth;
        int newSecondHeight = secondHeight;

        int thirdWidth = thirdPhoto.getWidth();
        int thirdHeight = thirdPhoto.getHeight();

        int newThirdWidth = thirdWidth;
        int newThirdHeight = thirdHeight;

        float resizeCoeff;

        if (firstWidth >= secondWidth) {
            resizeCoeff = (float) firstWidth / secondWidth;
            newFirstHeight = (int) (newFirstHeight / resizeCoeff);
            newFirstWidth = (int) (newFirstWidth / resizeCoeff);
        } else {
            resizeCoeff = (float) secondWidth / firstWidth;
            newSecondHeight = (int) (newSecondHeight / resizeCoeff);
            newSecondWidth = (int) (newSecondWidth / resizeCoeff);
        }
        resizeCoeff = (float) thirdWidth / firstWidth;
        newThirdWidth = (int) (newThirdWidth / resizeCoeff);
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);

        int subItemsTotalHeight = newFirstHeight + newSecondHeight + newThirdHeight;
        resizeCoeff = (float) newMainHeight / (subItemsTotalHeight);

        newMainHeight = (int) (newMainHeight / resizeCoeff);
        newMainWidth = (int) (newMainWidth / resizeCoeff);

        int totalWidth = newMainWidth + newFirstWidth;
        resizeCoeff = (float) totalWidth / (photoContainerWidth - TWO_IMAGE_MIDDLE_OFFSET);

        newMainHeight = (int) (newMainHeight / resizeCoeff);
        newMainWidth = (int) (newMainWidth / resizeCoeff);

        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = newFirstWidth;

        newThirdWidth = newFirstWidth;
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);

        resizeCoeff = (float) mainHeight / (mainHeight - 2 * TWO_IMAGE_MIDDLE_OFFSET);

        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = newFirstWidth;

        newThirdWidth = newFirstWidth;
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(newMainWidth,
                newMainHeight);
        subContainerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        subContainerParams.setMargins(TWO_IMAGE_MIDDLE_OFFSET, 0, TWO_IMAGE_MIDDLE_OFFSET, 0);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);

        RelativeLayout.LayoutParams secondParams = new RelativeLayout.LayoutParams(newSecondWidth, newSecondHeight);
        secondParams.addRule(RelativeLayout.BELOW, R.id.news_photos_first_image);
        secondParams.setMargins(0, TWO_IMAGE_MIDDLE_OFFSET, 0, 0);
        secondImage.setLayoutParams(secondParams);

        RelativeLayout.LayoutParams thirdParams = new RelativeLayout.LayoutParams(newThirdWidth, newThirdHeight);
        thirdParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        thirdImage.setLayoutParams(thirdParams);
    }

    private void calculateBottomSubThreeViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, PhotosInfo secondPhoto,
                                              PhotosInfo thirdPhoto, ImageView mainImage, RelativeLayout photoSubContainer,
                                              ImageView firstImage, ImageView secondImage, ImageView thirdImage) {
        //Prepare main view
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();
        float resizeCoeff = (float) mainWidth / mainHeight;

        int newMainWidth = photoContainerWidth;
        int newMainHeight = (int) (newMainWidth / resizeCoeff);

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        // prepare sub items

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int secondWidth = secondPhoto.getWidth();
        int secondHeight = secondPhoto.getHeight();

        int thirdWidth = thirdPhoto.getWidth();
        int thirdHeight = thirdPhoto.getHeight();

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        int newSecondWidth = secondWidth;
        int newSecondHeight = secondHeight;

        int newThirdWidth = thirdWidth;
        int newThirdHeight = thirdHeight;

        if (firstHeight >= secondHeight) {
            resizeCoeff = (float) firstHeight / secondHeight;
            newFirstHeight = (int) (newFirstHeight / resizeCoeff);
            newFirstWidth = (int) (newFirstWidth / resizeCoeff);
        } else {
            resizeCoeff = (float) secondHeight / firstHeight;
            newSecondHeight = (int) (newSecondHeight / resizeCoeff);
            newSecondWidth = (int) (newSecondWidth / resizeCoeff);
        }

        resizeCoeff = (float) newThirdHeight / newFirstHeight;
        newThirdHeight = (int) (newThirdHeight / resizeCoeff);
        newThirdWidth = (int) (newThirdWidth / resizeCoeff);

        float totalWidth = photoContainerWidth - 2 * TWO_IMAGE_MIDDLE_OFFSET;
        resizeCoeff = (newFirstWidth + newSecondWidth + newThirdWidth) / totalWidth;
        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = (int) (newSecondWidth / resizeCoeff);

        newThirdHeight = (int) (newThirdHeight / resizeCoeff);
        newThirdWidth = (int) (newThirdWidth / resizeCoeff);

        //prepare subcontainer: width all parent. SubContainer leaves below main imageView
        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(photoContainerWidth, newFirstHeight);
        subContainerParams.addRule(RelativeLayout.BELOW, R.id.news_photos_main_image);
        subContainerParams.setMargins(0, TWO_IMAGE_MIDDLE_OFFSET, 0, 0);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);

        RelativeLayout.LayoutParams secondParams = new RelativeLayout.LayoutParams(newSecondWidth, newSecondHeight);
        secondParams.addRule(RelativeLayout.RIGHT_OF, R.id.news_photos_first_image);
        secondParams.setMargins(TWO_IMAGE_MIDDLE_OFFSET, 0, 0, 0);
        secondImage.setLayoutParams(secondParams);

        RelativeLayout.LayoutParams thirdParams = new RelativeLayout.LayoutParams(newThirdWidth, newThirdHeight);
        thirdParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        thirdImage.setLayoutParams(thirdParams);
    }

    private void calculateBottomSubViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, PhotosInfo secondPhoto,
                                         ImageView mainImage, RelativeLayout photoSubContainer,
                                         ImageView firstImage, ImageView secondImage) {
        //Prepare main view
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();
        float resizeCoeff = (float) mainWidth / mainHeight;

        int newMainWidth = photoContainerWidth;
        int newMainHeight = (int) (newMainWidth / resizeCoeff);

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        // prepare sub items

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int secondWidth = secondPhoto.getWidth();
        int secondHeight = secondPhoto.getHeight();

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        int newSecondWidth = secondWidth;
        int newSecondHeight = secondHeight;

        float totalWidth = photoContainerWidth - TWO_IMAGE_MIDDLE_OFFSET;
        if (firstHeight >= secondHeight) {
            resizeCoeff = (float) firstHeight / secondHeight;
            newFirstHeight = (int) (newFirstHeight / resizeCoeff);
            newFirstWidth = (int) (newFirstWidth / resizeCoeff);
        } else {
            resizeCoeff = (float) secondHeight / firstHeight;
            newSecondHeight = (int) (newSecondHeight / resizeCoeff);
            newSecondWidth = (int) (newSecondWidth / resizeCoeff);
        }

        resizeCoeff = (newFirstWidth + newSecondWidth) / totalWidth;
        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = (int) (newSecondWidth / resizeCoeff);

        //prepare subcontainer: width all parent. SubContainer leaves below main imageView
        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(photoContainerWidth, newFirstHeight);
        subContainerParams.addRule(RelativeLayout.BELOW, R.id.news_photos_main_image);
        subContainerParams.setMargins(0, TWO_IMAGE_MIDDLE_OFFSET, 0, 0);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);

        RelativeLayout.LayoutParams secondParams = new RelativeLayout.LayoutParams(newSecondWidth, newSecondHeight);
        secondParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        secondImage.setLayoutParams(secondParams);
    }

    private void calculateRightSubViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, PhotosInfo secondPhoto,
                                        ImageView mainImage, RelativeLayout photoSubContainer,
                                        ImageView firstImage, ImageView secondImage) {
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();

        int newMainHeight = mainHeight;
        int newMainWidth = mainWidth;

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        int secondWidth = secondPhoto.getWidth();
        int secondHeight = secondPhoto.getHeight();

        int newSecondWidth = secondWidth;
        int newSecondHeight = secondHeight;

        float resizeCoeff;

        if (firstWidth >= secondWidth) {
            resizeCoeff = (float) firstWidth / secondWidth;
            newFirstHeight = (int) (newFirstHeight / resizeCoeff);
            newFirstWidth = (int) (newFirstWidth / resizeCoeff);
        } else {
            resizeCoeff = (float) secondWidth / firstWidth;
            newSecondHeight = (int) (newSecondHeight / resizeCoeff);
            newSecondWidth = (int) (newSecondWidth / resizeCoeff);
        }
        int subItemsTotalHeight = newFirstHeight + newSecondHeight;
        float resizeCoeffMain = (float) newMainHeight / (subItemsTotalHeight + TWO_IMAGE_MIDDLE_OFFSET);

        newMainHeight = (int) (newMainHeight / resizeCoeffMain);
        newMainWidth = (int) (newMainWidth / resizeCoeffMain);

        int totalWidth = newMainWidth + newFirstWidth;
        resizeCoeff = (float) totalWidth / (photoContainerWidth - TWO_IMAGE_MIDDLE_OFFSET);
        resizeCoeffMain = (float) totalWidth / (photoContainerWidth);

        newMainHeight = (int) (newMainHeight / resizeCoeffMain);
        newMainWidth = (int) (newMainWidth / resizeCoeffMain);

        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newSecondHeight = (int) (newSecondHeight / resizeCoeff);
        newSecondWidth = newFirstWidth;

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(newFirstWidth,
                newMainHeight);
        subContainerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        subContainerParams.setMargins(TWO_IMAGE_MIDDLE_OFFSET, 0, 0, 0);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);

        RelativeLayout.LayoutParams secondParams = new RelativeLayout.LayoutParams(newSecondWidth, newSecondHeight);
        secondParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        secondImage.setLayoutParams(secondParams);
    }

    private void calculateTwoVerticalImageViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, ImageView mainImage,
                                                RelativeLayout photoSubContainer, ImageView firstImage) {
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int newMainHeight = mainHeight;
        int newMainWidth = mainWidth;

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        newMainWidth = (photoContainerWidth * 2) / 3;
        newFirstWidth = (photoContainerWidth * 2) / 3;

        float resizeCoeff = (float) mainWidth / mainHeight;
        newMainHeight = (int) (newMainWidth / resizeCoeff);

        resizeCoeff = (float) firstWidth / firstHeight;
        newFirstHeight = (int) (newFirstWidth / resizeCoeff);

        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(newFirstWidth,
                newFirstHeight);
        subContainerParams.addRule(RelativeLayout.BELOW, R.id.news_photos_main_image);
        subContainerParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        subContainerParams.setMargins(0, TWO_IMAGE_MIDDLE_OFFSET, 0, 0);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        firstParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);
    }

    private void calculateOneImageView(PhotosInfo mainPhoto, ImageView mainImage) {
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();

        int newMainHeight = mainHeight;
        int newMainWidth = mainWidth;

        float resizeCoeff;
        if (mainHeight >= mainWidth) {
            newMainHeight = photoContainerWidth;
            resizeCoeff = (float) mainHeight / mainWidth;
            newMainWidth = (int) (newMainHeight / resizeCoeff);
        } else {
            newMainWidth = photoContainerWidth;
            resizeCoeff = (float) mainWidth / mainHeight;
            newMainHeight = (int) (newMainWidth / resizeCoeff);
        }

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);
    }

    private void calculateTwoHorizontalImageViews(PhotosInfo mainPhoto, PhotosInfo firstPhoto, ImageView mainImage,
                                                  RelativeLayout photoSubContainer, ImageView firstImage) {
        int mainWidth = mainPhoto.getWidth();
        int mainHeight = mainPhoto.getHeight();

        int firstWidth = firstPhoto.getWidth();
        int firstHeight = firstPhoto.getHeight();

        int newMainHeight = mainHeight;
        int newMainWidth = mainWidth;

        int newFirstWidth = firstWidth;
        int newFirstHeight = firstHeight;

        float resizeCoeff;
        float totalWidth = photoContainerWidth - TWO_IMAGE_MIDDLE_OFFSET;
        if (firstHeight >= mainHeight) {
            resizeCoeff = (float) firstHeight / mainHeight;
            newFirstHeight = (int) (newFirstHeight / resizeCoeff);
            newFirstWidth = (int) (newFirstWidth / resizeCoeff);
        } else {
            resizeCoeff = (float) mainHeight / firstHeight;
            newMainHeight = (int) (newMainHeight / resizeCoeff);
            newMainWidth = (int) (newMainWidth / resizeCoeff);
        }

        resizeCoeff = (newFirstWidth + newMainWidth) / totalWidth;
        newFirstHeight = (int) (newFirstHeight / resizeCoeff);
        newFirstWidth = (int) (newFirstWidth / resizeCoeff);

        newMainHeight = (int) (newMainHeight / resizeCoeff);
        newMainWidth = (int) (newMainWidth / resizeCoeff);

        RelativeLayout.LayoutParams subContainerParams = new RelativeLayout.LayoutParams(newFirstWidth,
                newFirstHeight);
        subContainerParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        photoSubContainer.setLayoutParams(subContainerParams);

        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(newMainWidth, newMainHeight);
        mainParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mainImage.setLayoutParams(mainParams);

        RelativeLayout.LayoutParams firstParams = new RelativeLayout.LayoutParams(newFirstWidth, newFirstHeight);
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        firstImage.setLayoutParams(firstParams);
    }

    public boolean isHorizontal(PhotosInfo photo) {
        int height = photo.getHeight();
        int width = photo.getWidth();
        return width >= height;
    }

    public boolean isRectangularHorizontal(PhotosInfo photo) {
        int height = photo.getHeight();
        int width = photo.getWidth();
        return ((width)) * 2 / 3 >= height;
    }
}

