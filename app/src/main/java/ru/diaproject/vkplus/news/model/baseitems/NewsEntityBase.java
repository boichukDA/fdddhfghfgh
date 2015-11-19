package ru.diaproject.vkplus.news.model.baseitems;

import ru.diaproject.vkplus.news.model.items.CopyHistory;

public abstract class NewsEntityBase<T>  {
    private FilterType type;
    private Integer sourceId;
    private Integer date;
    private Integer finalPost;
    private Boolean canEdit;
    private Boolean canDelete;
    private Integer postId;
    private T attachments;

    private CopyHistory copyHistory;

    public NewsEntityBase(FilterType type){
        this.type = type;
    }

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getFinalPost() {
        return finalPost;
    }

    public void setFinalPost(Integer finalPost) {
        this.finalPost = finalPost;
    }
    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public CopyHistory getCopyHistory() {
        return copyHistory;
    }

    public void setCopyHistory(CopyHistory copyHistory) {
        this.copyHistory = copyHistory;
    }

    public  void setAttachments(T attachments){
        this.attachments = attachments;
    }
    public  T getAttachments(){
        return attachments;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        return this.sourceId.equals(((NewsEntityBase) o).getSourceId());
    }

    @Override
    public int hashCode() {
        return sourceId.hashCode();
    }

}
