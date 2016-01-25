package ru.diaproject.vkplus.news.binders;

import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.news.NewsUserConfig;
import ru.diaproject.vkplus.news.binders.bindhelpers.AudioBindHelper;
import ru.diaproject.vkplus.news.binders.bindhelpers.CopyHistoryBindHelper;
import ru.diaproject.vkplus.news.binders.bindhelpers.VideoBindHelper;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.NewsResponse;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.baseitems.IDataPostItem;
import ru.diaproject.vkplus.news.model.items.CommentsInfo;
import ru.diaproject.vkplus.news.model.items.CopyHistory;
import ru.diaproject.vkplus.news.model.items.LikesInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.RepostsInfo;
import ru.diaproject.vkplus.news.model.users.IDataOwner;
import ru.diaproject.vkplus.news.model.users.OwnerSex;
import ru.diaproject.vkplus.news.viewholders.PostItemViewHolder;

public class PostItemBinder extends DataPhotosBinder<PostItemViewHolder, IDataPostItem> {
    private static final int MAX_VIDEOS_DISPLAY = 4;
    private static final int MAX_AUDIOS_DISPLAY = 4;
    private static final int TOTAL_COPY_HISTORY_OFFSET = 10;

    private final SparseBooleanArray mCollapsedStatus;
    private final CopyHistoryBindHelper copyHistoryBinder;
    private final VideoBindHelper videoBindHelper;
    private final AudioBindHelper audioBindHelper;
    private NewsPagerCardFragment parent;
    private NewsUserConfig configuration;

    public PostItemBinder(DataBindAdapter dataBindAdapter, NewsResponse items, NewsPagerCardFragment fragment, SparseBooleanArray mCollapsedStatus) {
        super(fragment.getContext(), dataBindAdapter, items);
        this.parent = fragment;
        this.mCollapsedStatus = mCollapsedStatus;
        this.configuration = parent.getUser().getNewsUserConfiguration();
        copyHistoryBinder = new CopyHistoryBindHelper(getContext(), getTotalPixelsOffset(), TOTAL_COPY_HISTORY_OFFSET, getDensity(),
                MAX_VIDEOS_DISPLAY, MAX_AUDIOS_DISPLAY, parent.getUser().getNewsUserConfiguration());
        videoBindHelper = new VideoBindHelper(getContext());
        audioBindHelper = new AudioBindHelper(parent.getContext(), MAX_AUDIOS_DISPLAY, configuration);
    }

    @Override
    public PostItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_postview_item, parent, false);
        return new PostItemViewHolder(v);
    }

    @Override
    public void bindViewHolder(final PostItemViewHolder holder, final int position) {
        IDataPostItem entity = getItem(position);
        IDataOwner owner = entity.getItemOwner();

        setDataOwner(holder, entity);

        if (entity.getText() == null || "".equals(entity.getText()))
            holder.mainText.setVisibility(View.GONE);
        else {
            holder.mainText.setText(entity.getSpannableText(), mCollapsedStatus, position);
            holder.mainText.setVisibility(View.VISIBLE);
        }

        if (entity.getPostSourceInfo() != null
                && parent.getContext().getString(R.string.news_post_profile_photo).equals(entity.getPostSourceInfo().getData())) {
            holder.mainText.setVisibility(View.VISIBLE);
            if (owner.getSex().equals(OwnerSex.MAN))
                holder.mainText.setText(parent.getString(R.string.news_photo_update_man));
            else
                holder.mainText.setText(parent.getString(R.string.news_photo_update_woman));
        }

        Photos photos = entity.getAttachmentPhotos();

        if (photos != null) {
            setPhotos(photos, holder);
        } else
            hidePhotos(holder);

        List<VideoInfo> videos = entity.getAttachmentVideos();
        if (!(videos == null || videos.size() == 0))
            videoBindHelper.setData(videos, holder.mainVideosholder, MAX_VIDEOS_DISPLAY);
        else
            videoBindHelper.hideLayout(holder.mainVideosholder);

        List<AudioInfo> audios = entity.getAttachmentAudios();

        if (!(audios == null || audios.size() == 0))
            audioBindHelper.setData(audios, holder.mainAudiosHolder);
        else
            audioBindHelper.hideLayout(holder.mainAudiosHolder);
        CopyHistory history = entity.getCopyHistory();

        if (history != null && !history.getItems().isEmpty()) {

            holder.copyHistoryLayout.setVisibility(View.VISIBLE);
            copyHistoryBinder.setData(history.getItems().get(0), holder.firstCopyHistory, mCollapsedStatus, position);

            if (history.getItems().size()>=2)
                copyHistoryBinder.setData(history.getItems().get(1), holder.secondCopyHistory, mCollapsedStatus, position);
            else copyHistoryBinder.hideLayout(holder.secondCopyHistory);

        } else holder.copyHistoryLayout.setVisibility(View.GONE);

        LikesInfo likesInfo = entity.getLikes();
        holder.postLikeImage.setImageBitmap(Bitmap.createBitmap(configuration.getPostLikeBitmap()));
        holder.postLikeCount.setText(String.valueOf(likesInfo.getCount()));
        if (likesInfo.getUserLikes())
            ViewCompat.setAlpha(holder.postLikeImage, 0.5f);
        else ViewCompat.setAlpha(holder.postLikeImage, 1f);

        if (likesInfo.getCanLikes()) {
            holder.postLikeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Observable<Integer>.from(VKMainExecutor.request())
                }
            });
        }

        CommentsInfo commentsInfo = entity.getComments();

        holder.postCommentImage.setImageBitmap(configuration.getPostCommentBitmap());
        holder.postCommentCount.setText(String.valueOf(commentsInfo.getCount()));
        if (!commentsInfo.getCanPost())
            holder.postCommentLayout.setVisibility(View.INVISIBLE);
        else holder.postCommentLayout.setVisibility(View.VISIBLE);

        RepostsInfo reposts = entity.getReposts();

        holder.postShareImage.setImageBitmap(configuration.getPostShareBitmap());
        holder.postShareCount.setText(String.valueOf(reposts.getCount()));
        if (reposts.getUserReposted())
            ViewCompat.setAlpha(holder.postShareImage, 0.5f);
        else ViewCompat.setAlpha(holder.postShareImage, 1f);
    }
}
