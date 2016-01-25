package ru.diaproject.vkplus.news;

import android.graphics.Bitmap;

import java.io.Serializable;

public class NewsUserConfig implements Serializable {
    private Bitmap audioPlayBitmap;
    private Bitmap postLikeButton;
    private Bitmap postCommentButton;
    private Bitmap postShareBitmap;

    public Bitmap getAudioPlayBitmap() {
        return audioPlayBitmap;
    }

    public void setAudioPlayBitmap(Bitmap audioPlayBitmap) {
        this.audioPlayBitmap = audioPlayBitmap;
    }

    public void setPostLikeBitmap(Bitmap postLikeButton) {
        this.postLikeButton = postLikeButton;
    }

    public Bitmap getPostLikeBitmap() {
        return postLikeButton;
    }

    public void setPostCommentBitmap(Bitmap postCommentButton) {
        this.postCommentButton = postCommentButton;
    }

    public Bitmap getPostCommentBitmap() {
        return postCommentButton;
    }

    public void setPostShareBitmap(Bitmap postShareBitmap) {
        this.postShareBitmap = postShareBitmap;
    }

    public Bitmap getPostShareBitmap() {
        return postShareBitmap;
    }
}
