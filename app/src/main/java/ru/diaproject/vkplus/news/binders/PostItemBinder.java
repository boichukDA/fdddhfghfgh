package ru.diaproject.vkplus.news.binders;

import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.database.model.ColorScheme;
import ru.diaproject.vkplus.database.model.NewsConfiguration;
import ru.diaproject.vkplus.news.binders.bindhelpers.AudioBindHelper;
import ru.diaproject.vkplus.news.binders.bindhelpers.CopyHistoryBindHelper;
import ru.diaproject.vkplus.news.binders.bindhelpers.GifBindHelper;
import ru.diaproject.vkplus.news.binders.bindhelpers.VideoBindHelper;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.model.NewsResponse;
import ru.diaproject.vkplus.model.attachments.audios.AudioInfo;
import ru.diaproject.vkplus.model.attachments.VideoInfo;
import ru.diaproject.vkplus.model.attachments.doc.DocInfo;
import ru.diaproject.vkplus.model.newsitems.IDataPostItem;
import ru.diaproject.vkplus.model.items.CommentsInfo;
import ru.diaproject.vkplus.model.newsitems.copyhistory.CopyHistory;
import ru.diaproject.vkplus.model.items.LikesInfo;
import ru.diaproject.vkplus.model.attachments.photos.Photos;
import ru.diaproject.vkplus.model.items.RepostsInfo;
import ru.diaproject.vkplus.model.users.IDataOwner;
import ru.diaproject.vkplus.model.users.OwnerSex;
import ru.diaproject.vkplus.news.viewholders.PostItemViewHolder;

public class PostItemBinder extends DataPhotosBinder<PostItemViewHolder, IDataPostItem> {
    public static final int MAX_VIDEOS_DISPLAY = 4;
    public static final int MAX_AUDIOS_DISPLAY = 4;
    public static final int MAX_GIF_DISPLAY = 3;
    public static final int TOTAL_COPY_HISTORY_OFFSET = 10;

    private final SparseBooleanArray mCollapsedStatus;
    private final CopyHistoryBindHelper copyHistoryBinder;

    private final GifBindHelper gifBindHelper;
    private final VideoBindHelper videoBindHelper;
    private final AudioBindHelper audioBindHelper;
    private final NewsConfiguration configuration;
    private NewsPagerCardFragment parent;
    private ColorScheme colorScheme;

    public PostItemBinder(DataBindAdapter dataBindAdapter, NewsResponse items, NewsPagerCardFragment fragment, SparseBooleanArray mCollapsedStatus) {
        super(fragment.getContext(), dataBindAdapter, items);

        this.parent = fragment;
        colorScheme = parent.getUser().getColorScheme();

        this.mCollapsedStatus = mCollapsedStatus;
        this.configuration = parent.getUser().getNewsConfiguration();

        copyHistoryBinder = new CopyHistoryBindHelper(getContext(), getTotalPixelsOffset(), TOTAL_COPY_HISTORY_OFFSET, getDensity(),
                MAX_VIDEOS_DISPLAY, MAX_AUDIOS_DISPLAY, MAX_GIF_DISPLAY, parent.getUser().getNewsConfiguration());

        videoBindHelper = new VideoBindHelper(getContext());
        audioBindHelper = new AudioBindHelper(parent.getContext(), MAX_AUDIOS_DISPLAY, configuration);
        gifBindHelper = new GifBindHelper(parent.getContext(), MAX_GIF_DISPLAY);
    }

    @Override
    public PostItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_postview_item, parent, false);

        PostItemViewHolder holder = new PostItemViewHolder(v);
        ((CardView)holder.itemView).setCardBackgroundColor(colorScheme.getCardColor());
        holder.copyHistoryRightView.setBackgroundColor(colorScheme.getMainColor());
        holder.mainText.setTextColor(colorScheme.getTextColor());
        holder.applyColorScheme(colorScheme);

        holder.mainVideosholder.applyColorScheme(colorScheme);
        holder.mainAudiosHolder.applyColorScheme(colorScheme);
        holder.mainGifViewHolder.applyColorScheme(colorScheme);

        holder.firstCopyHistory.applyColorScheme(colorScheme);

        holder.postLikeCount.setTextColor(colorScheme.getTextColor());
        holder.postCommentCount.setTextColor(colorScheme.getTextColor());
        holder.postShareCount.setTextColor(colorScheme.getTextColor());
        return holder;
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

        List<DocInfo> docs = entity.getAttachmentDocs();
        if (!(docs == null || docs.size() == 0))
            gifBindHelper.setData(docs, holder.mainGifViewHolder);
        else
            gifBindHelper.hideLayout(holder.mainGifViewHolder);

        if (photos != null) {
            setPhotos(entity, holder);
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

        } else holder.copyHistoryLayout.setVisibility(View.GONE);

        LikesInfo likesInfo = entity.getLikes();
        holder.postLikeImage.setImageBitmap(Bitmap.createBitmap(configuration.getPostLikeButton()));
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

        holder.postCommentImage.setImageBitmap(configuration.getPostCommentButton());
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
