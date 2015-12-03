package ru.diaproject.vkplus.news.binders;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.diaproject.vkplus.R;
import ru.diaproject.vkplus.core.databinders.DataBindAdapter;
import ru.diaproject.vkplus.core.databinders.DataBinder;
import ru.diaproject.vkplus.core.executor.VKMainExecutor;
import ru.diaproject.vkplus.core.utils.BitmapUtils;
import ru.diaproject.vkplus.news.fragments.NewsPagerCardFragment;
import ru.diaproject.vkplus.news.model.Response;
import ru.diaproject.vkplus.news.model.attachments.Attachments;

import ru.diaproject.vkplus.news.model.attachments.AudioInfo;
import ru.diaproject.vkplus.news.model.attachments.VideoInfo;
import ru.diaproject.vkplus.news.model.baseitems.NewsPostItem;
import ru.diaproject.vkplus.news.model.groups.Group;
import ru.diaproject.vkplus.news.model.items.CommentsInfo;
import ru.diaproject.vkplus.news.model.items.CopyHistoryInfo;
import ru.diaproject.vkplus.news.model.items.LikesInfo;
import ru.diaproject.vkplus.news.model.items.Photos;
import ru.diaproject.vkplus.news.model.items.RepostsInfo;
import ru.diaproject.vkplus.news.model.users.User;

import ru.diaproject.vkplus.news.viewholders.PostItemViewHolder;
import ru.diaproject.vkplus.news.views.NewsPostCopyHistoryLayout;

public class PostItemBinder extends DataBinder<PostItemViewHolder> {
    private NewsPagerCardFragment parent;
    private Response items;
    private final SparseBooleanArray mCollapsedStatus;

    public PostItemBinder(DataBindAdapter dataBindAdapter, Response items, NewsPagerCardFragment fragment, SparseBooleanArray mCollapsedStatus) {
        super(dataBindAdapter);
        this.parent = fragment;
        this.items = items;
        this.mCollapsedStatus = mCollapsedStatus;
    }

    @Override
    public PostItemViewHolder newViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_postview_item, parent, false);
        return new PostItemViewHolder(v);
    }

    @Override
    public void bindViewHolder(final PostItemViewHolder holder, final int position) {
        final NewsPostItem entity = (NewsPostItem) items.getItems().get(position);
        VKMainExecutor.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                Integer sourceId = entity.getSourceId();
                Integer positiveSourceId = Math.abs(sourceId);
                String text = "";
                String imageUrl = "";
                Byte sex = 2;
                if (sourceId > 0 && items.getProfiles().containsKey(sourceId) ) {
                    User user = items.getProfiles().get(sourceId);
                    sex = user.getSex();
                    text = user.getFirstName() + " " + user.getLastName();
                    imageUrl = user.getPhoto100();
                }
                if (sourceId < 0 && items.getGroups().containsKey(positiveSourceId) ) {
                    Group group = items.getGroups().get(positiveSourceId);
                    text = group.getName();
                    sex = 0;
                    imageUrl = group.getPhoto100();
                }

                final String finalText = text;
                final String finalImageUrl = imageUrl;
                final Byte finalSex1 = sex;

                ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        holder.header.setData(finalText, finalImageUrl, entity.getDate(), finalSex1);
                    }
                });

                ((Activity)parent.getContext()).runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        if (entity.getText() == null || "".equals(entity.getText()))
                            holder.mainText.setVisibility(View.GONE);
                        else {
                            holder.mainText.setText(entity.getText(),mCollapsedStatus, position);
                            holder.mainText.setVisibility(View.VISIBLE);
                        }
                    }
                });

                final Byte finalSex = sex;
                ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (entity.getPostSourceInfo() != null
                                && parent.getContext().getString(R.string.news_post_profile_photo).equals(entity.getPostSourceInfo().getData())) {
                            holder.mainText.setVisibility(View.VISIBLE);
                            if (finalSex.equals(Byte.valueOf("2")))
                                holder.mainText.setText(parent.getString(R.string.news_photo_update_man));
                            else
                                holder.mainText.setText(parent.getString(R.string.news_photo_update_woman));
                        }
                    }
                });

                if (entity.getAttachments()!= null) {

                    Attachments attachments = entity.getAttachments();
                    final Photos photos = attachments.getPhotos();

                    ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              holder.attachmentContainer.setVisibility(View.VISIBLE);
                              holder.attachmentContainer.setPhotos(photos, entity.getSourceId(),
                                      entity.getDate(), parent);
                          }
                      });

                    final List<VideoInfo> videos = attachments.getVideos();
                    ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!(videos == null || videos.size() == 0))
                                holder.attachmentContainer.setVideos(videos);
                            else holder.attachmentContainer.hideVideoLayout();
                        }
                    });
                    final List<AudioInfo> audios = attachments.getAudios();

                    ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!(audios == null || audios.size() == 0))
                            holder.attachmentContainer.setAudios(audios);
                            else holder.attachmentContainer.hideAudioLayout();
                        }
                    });
                }
                else
                    ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.attachmentContainer.setVisibility(View.GONE);
                        }
                    });

                if (entity.getCopyHistory() != null){
                    List<CopyHistoryInfo> historyList = entity.getCopyHistory().getItems();
                    ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.copyHistoryLayout.setVisibility(View.VISIBLE);
                        }
                    });

                    if (historyList.size() == 1){
                        ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.firstCopyHistory.setVisibility(View.VISIBLE);
                                holder.secondCopyHistory.setVisibility(View.GONE);
                            }
                        });

                        holder.firstCopyHistory.setData(historyList.get(0),items,mCollapsedStatus,position, parent);
                    }

                    if (historyList.size() == 2){

                        ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.firstCopyHistory.setVisibility(View.VISIBLE);
                                holder.secondCopyHistory.setVisibility(View.VISIBLE);
                            }
                        });

                        holder.firstCopyHistory.setData(historyList.get(0),items,mCollapsedStatus,position, parent);
                        holder.secondCopyHistory.setData(historyList.get(1),items,mCollapsedStatus,position, parent);
                    }
                    if (historyList.size()>2)
                        for (int index = 2;index < historyList.size();index++){
                            final NewsPostCopyHistoryLayout layout = new NewsPostCopyHistoryLayout(parent.getContext());
                            layout.setData(historyList.get(index), items, mCollapsedStatus, position, parent);
                            ((Activity)parent.getContext()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.innerCopyHistoryLayout.addView(layout);
                                }
                            });

                        }
                }
                else holder.copyHistoryLayout.setVisibility(View.GONE);

                final LikesInfo likesInfo = entity.getLikes();
                VKMainExecutor.INSTANCE.execute(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) parent.getContext()).runOnUiThread(new Runnable() {
                            Bitmap bitmap = BitmapUtils.appyColorFilterForResource(parent.getContext(), R.drawable.news_post_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                            @Override
                            public void run() {
                                holder.postLikeImage.setImageBitmap(bitmap);
                                holder.postLikeCount.setText(String.valueOf(likesInfo.getCount()));
                                if (likesInfo.getUserLikes())
                                    ViewCompat.setAlpha(holder.postLikeImage, 0.5f);
                                else ViewCompat.setAlpha(holder.postLikeImage, 1f);
                            }
                        });
                    }
                });

                final CommentsInfo commentsInfo = entity.getComments();
                VKMainExecutor.INSTANCE.execute(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) parent.getContext()).runOnUiThread(new Runnable() {
                            Bitmap bitmap = BitmapUtils.appyColorFilterForResource(parent.getContext(), R.drawable.news_post_comment, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                            @Override
                            public void run() {
                                holder.postCommentImage.setImageBitmap(bitmap);
                                holder.postCommentCount.setText(String.valueOf(commentsInfo.getCount()));
                                if (!commentsInfo.getCanPost())
                                    holder.postCommentLayout.setVisibility(View.INVISIBLE);
                                else holder.postCommentLayout.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
                final RepostsInfo reposts = entity.getReposts();
                VKMainExecutor.INSTANCE.execute(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) parent.getContext()).runOnUiThread(new Runnable() {
                            Bitmap bitmap = BitmapUtils.appyColorFilterForResource(parent.getContext(), R.drawable.news_share_like, R.color.m_indigo, PorterDuff.Mode.MULTIPLY);

                            @Override
                            public void run() {
                                holder.postShareImage.setImageBitmap(bitmap);
                                holder.postShareCount.setText(String.valueOf(reposts.getCount()));
                                if (reposts.getUserReposted())
                                    ViewCompat.setAlpha(holder.postShareImage, 0.5f);
                                else ViewCompat.setAlpha(holder.postShareImage, 1f);
                            }
                        });
                    }
                });
            }
        });

    }
}
