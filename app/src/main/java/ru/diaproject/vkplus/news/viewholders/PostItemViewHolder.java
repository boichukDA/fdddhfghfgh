package ru.diaproject.vkplus.news.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;

import ru.diaproject.ui.circularimageview.RobotoExpandableTextView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.viewholders.items.CopyHistoryViewHolder;
import ru.diaproject.vkplus.news.viewholders.items.DataAudiosViewHolder;
import ru.diaproject.vkplus.news.viewholders.items.DataGifsViewHolder;
import ru.diaproject.vkplus.news.viewholders.items.DataVideosViewHolder;

public class PostItemViewHolder extends DataPhotosViewHolder{
    public RobotoExpandableTextView mainText;
    public DataGifsViewHolder mainGifViewHolder;
    public DataVideosViewHolder mainVideosholder;
    public DataAudiosViewHolder mainAudiosHolder;

    public LinearLayout copyHistoryLayout;
    public CopyHistoryViewHolder firstCopyHistory;
    public CopyHistoryViewHolder secondCopyHistory;

    public LinearLayout postLikeLayout;
    public ImageView postLikeImage;
    public RobotoTextView postLikeCount;
    public LinearLayout postCommentLayout;
    public ImageView postCommentImage;
    public RobotoTextView postCommentCount;
    public LinearLayout postShareLayout;
    public ImageView postShareImage;
    public RobotoTextView postShareCount;

    public PostItemViewHolder(View itemView) {
        super(itemView);
        mainText = (RobotoExpandableTextView) itemView.findViewById(R.id.news_post_main_text);
        mainVideosholder = new DataVideosViewHolder(itemView);
        mainAudiosHolder = new DataAudiosViewHolder(itemView);

        View docView = itemView.findViewById(R.id.news_gif_layout);
        mainGifViewHolder = new DataGifsViewHolder(docView);

        copyHistoryLayout = (LinearLayout) itemView.findViewById(R.id.news_post_history_layout);

        View v = itemView.findViewById(R.id.news_post_copy_history_first);
        firstCopyHistory = new CopyHistoryViewHolder(v);

        postLikeLayout = (LinearLayout) itemView.findViewById(R.id.news_post_like_layout);
        postLikeImage = (ImageView) itemView.findViewById(R.id.news_post_like_image);
        postLikeCount = (RobotoTextView) itemView.findViewById(R.id.news_post_like_count);

        postCommentLayout = (LinearLayout) itemView.findViewById(R.id.news_post_comment_layout);
        postCommentImage = (ImageView) itemView.findViewById(R.id.news_post_comment_image);
        postCommentCount = (RobotoTextView) itemView.findViewById(R.id.news_post_comment_count);
        postShareLayout = (LinearLayout) itemView.findViewById(R.id.news_post_share_layout);
        postShareImage = (ImageView) itemView.findViewById(R.id.news_post_share_image);
        postShareCount = (RobotoTextView) itemView.findViewById(R.id.news_post_share_count);
    }

    public void clear() {
        super.clear();

        if (mainVideosholder != null)
            mainVideosholder.clear();

        if (mainAudiosHolder != null)
            mainAudiosHolder.clear();

        if (firstCopyHistory != null)
            firstCopyHistory.clear();
    }
}
