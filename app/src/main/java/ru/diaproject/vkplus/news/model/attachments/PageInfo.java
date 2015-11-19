package ru.diaproject.vkplus.news.model.attachments;

public class PageInfo {
    private Integer id;
    private Integer groupId;
    private Integer creatorId;
    private String title;
    private Boolean currentUserCanEdit;
    private Boolean currentUserCanEditAccess;
    private Byte whoCanView;
    private Byte whoCanEdit;
    private Integer edited;
    private Integer created;
    private Integer editorId;
    private Integer views;
    private String parent;
    private String parent2;
    private String source;
    private String html;
    private String viewUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCurrentUserCanEdit() {
        return currentUserCanEdit;
    }

    public void setCurrentUserCanEdit(Boolean currentUserCanEdit) {
        this.currentUserCanEdit = currentUserCanEdit;
    }

    public Boolean getCurrentUserCanEditAccess() {
        return currentUserCanEditAccess;
    }

    public void setCurrentUserCanEditAccess(Boolean currentUserCanEditAccess) {
        this.currentUserCanEditAccess = currentUserCanEditAccess;
    }

    public Byte getWhoCanView() {
        return whoCanView;
    }

    public void setWhoCanView(Byte whoCanView) {
        this.whoCanView = whoCanView;
    }

    public Byte getWhoCanEdit() {
        return whoCanEdit;
    }

    public void setWhoCanEdit(Byte whoCanEdit) {
        this.whoCanEdit = whoCanEdit;
    }

    public Integer getEdited() {
        return edited;
    }

    public void setEdited(Integer edited) {
        this.edited = edited;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public Integer getEditorId() {
        return editorId;
    }

    public void setEditorId(Integer editorId) {
        this.editorId = editorId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent2() {
        return parent2;
    }

    public void setParent2(String parent2) {
        this.parent2 = parent2;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }
}
