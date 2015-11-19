package ru.diaproject.vkplus.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoExpandableTextView;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.ui.circularimageview.CircularImageView;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.views.NewsHeaderView;
import ru.diaproject.vkplus.news.views.NewsPostAttachmentContainer;
import ru.diaproject.vkplus.news.views.NewsPostCopyHistoryLayout;

public class PostItemViewHolder extends RecyclerView.ViewHolder{

    @Bind(R.id.news_main_attachment)
    public NewsPostAttachmentContainer attachmentContainer;

    @Bind(R.id.news_header_layout)
    public NewsHeaderView header;

    @Bind(R.id.news_post_main_text)
    public RobotoExpandableTextView mainText;

    @Bind(R.id.news_post_history_layout)
    public LinearLayout copyHistoryLayout;

    @Bind(R.id.news_copy_history_layout)
    public LinearLayout innerCopyHistoryLayout;

    @Bind(R.id.news_post_first_inner_history)
    public NewsPostCopyHistoryLayout firstCopyHistory;

    @Bind(R.id.news_post_second_inner_history)
    public NewsPostCopyHistoryLayout secondCopyHistory;

    @Bind(R.id.news_post_like_layout)
    public LinearLayout postLikeLayout;

    @Bind(R.id.news_post_like_image)
    public ImageView postLikeImage;

    @Bind(R.id.news_post_like_count)
    public RobotoTextView postLikeCount;

    @Bind(R.id.news_post_comment_layout)
    public LinearLayout postCommentLayout;

    @Bind(R.id.news_post_comment_image)
    public ImageView postCommentImage;

    @Bind(R.id.news_post_comment_count)
    public RobotoTextView postCommentCount;

    @Bind(R.id.news_post_share_layout)
    public LinearLayout postShareLayout;

    @Bind(R.id.news_post_share_image)
    public ImageView postShareImage;

    @Bind(R.id.news_post_share_count)
    public RobotoTextView postShareCount;

    public PostItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void clear(NewsPagerCardFragment newsPagerCardFragment) {
        if (attachmentContainer != null)
            attachmentContainer.clear(newsPagerCardFragment);

        if (firstCopyHistory!= null)
            firstCopyHistory.clear(newsPagerCardFragment);

        if (secondCopyHistory!= null)
            secondCopyHistory.clear(newsPagerCardFragment);
    }
}
