package ru.diaproject.vkplus.model.attachments;


import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.model.IDataResult;
import ru.diaproject.vkplus.model.newsitems.DataItem;

public class VideoInfo extends DataItem implements IDataResult{
    private String title;
    private String description;
    private Integer duration;
    private String photo130;
    private String photo320;
    private String photo640;
    private Integer date;
    private Integer addingDate;
    private Integer views;
    private Integer comments;
    private String player;
    private String accessKey;
    private Boolean processing;

    private String convertedDuration;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPhoto130() {
        return photo130;
    }

    public void setPhoto130(String photo130) {
        this.photo130 = photo130;
    }

    public String getPhoto320() {
        return photo320;
    }

    public void setPhoto320(String photo320) {
        this.photo320 = photo320;
    }

    public String getPhoto640() {
        return photo640;
    }

    public void setPhoto640(String photo640) {
        this.photo640 = photo640;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Integer addingDate) {
        this.addingDate = addingDate;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public Boolean getProcessing() {
        return processing;
    }

    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    @Override
    public void prepareItems() {
       setConvertedDuration(DateUtils.toVideoTimeFormat(duration));
    }

    public String getConvertedDuration() {
        return convertedDuration;
    }

    public void setConvertedDuration(String convertedDuration) {
        this.convertedDuration = convertedDuration;
    }
}
