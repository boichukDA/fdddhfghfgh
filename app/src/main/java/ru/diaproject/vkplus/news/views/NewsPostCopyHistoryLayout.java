package ru.diaproject.vkplus.news.views;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ru.diaproject.ui.circularimageview.RobotoExpandableTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.model.attachments.Attachments;
import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.users.User;
import ru.diaproject.vkplus.vkcore.user.VKUser;

public class NewsPostCopyHistoryLayout extends LinearLayout{
    @Bind(R.id.news_post_copy_history_header)
    public NewsHeaderView copyHistoryHeader;

    @Bind(R.id.news_copy_history_text)
    public RobotoExpandableTextView copyTextView;

    @Bind(R.id.news_copy_history_attachment)
    public NewsPostAttachmentContainer attachmentCopyHistoryContainer;

    public NewsPostCopyHistoryLayout(Context context) {
        super(context);
        init();
    }

    public NewsPostCopyHistoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.news_post_copyhistory_layout, (ViewGroup) getRootView(), false);
        ButterKnife.bind(this, view);

        addView(view);
        this.setVisibility(GONE);
    }

    public void setData(final CopyHistoryInfo history, final Response items, final SparseBooleanArray mCollapsedStatus, final int position, final NewsPagerCardFragment parent){
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                final Integer copySourceId = history.getOwnerId();
                Integer positiveCopySourceId = Math.abs(copySourceId);
                String copyText = "";
                String copyImageUrl = "";
                Byte copySex = 2;
                if (copySourceId > 0 && items.getProfiles().containsKey(copySourceId) ) {
                    User user = items.getProfiles().get(copySourceId);
                    copySex = user.getSex();
                    copyText = user.getFirstName() + " " + user.getLastName();
                    copyImageUrl = user.getPhoto100();
                }
                if (copySourceId < 0 && items.getGroups().containsKey(positiveCopySourceId) ) {
                    Group group = items.getGroups().get(positiveCopySourceId);
                    copyText = group.getName();
                    copySex = 0;
                    copyImageUrl = group.getPhoto100();
                }

                final Byte finalCopySex = copySex;
                final String finalCopyImageUrl = copyImageUrl;
                final String finalCopyText = copyText;
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (history.getText() == null || "".equals(history.getText()))
                            copyTextView.setVisibility(View.GONE);
                        else {
                            copyTextView.setText(history.getText(),mCollapsedStatus, position);
                            copyTextView.setVisibility(View.VISIBLE);
                        }

                        copyHistoryHeader.setData(finalCopyText, finalCopyImageUrl, history.getDate(), finalCopySex);
                    }
                });


                if (history.getAttachments()!= null) {

                    attachmentCopyHistoryContainer.setVisibility(View.VISIBLE);

                    Attachments attachments = history.getAttachments();
                    final Photos photos = attachments.getPhotos();

                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            attachmentCopyHistoryContainer.setPhotos(photos, copySourceId, history.getDate(), parent);
                        }
                    });

                    final List<VideoInfo> videos = attachments.getVideos();
                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           if (!(videos == null || videos.size() == 0))
                               attachmentCopyHistoryContainer.setVideos(videos);
                           else
                               attachmentCopyHistoryContainer.hideVideoLayout();
                       }
                   });
                     final List < AudioInfo > audios = attachments.getAudios();
                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        if(!(audios == null || audios.size() == 0))
                            attachmentCopyHistoryContainer.setAudios(audios);
                        else attachmentCopyHistoryContainer.hideAudioLayout();
                            }
                    });
                }
                else
                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            attachmentCopyHistoryContainer.setVisibility(View.GONE);
                        }
                    });
            }
        });
    }

    public void clear() {
        if (copyHistoryHeader != null)
            copyHistoryHeader.clear();

        if (attachmentCopyHistoryContainer!= null)
            attachmentCopyHistoryContainer.clear();
    }
}
