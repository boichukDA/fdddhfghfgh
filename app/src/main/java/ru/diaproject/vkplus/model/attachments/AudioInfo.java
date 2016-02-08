package ru.diaproject.vkplus.model.attachments;

import ru.diaproject.vkplus.core.utils.DateUtils;
import ru.diaproject.vkplus.model.IDataResult;
import ru.diaproject.vkplus.model.baseitems.DataItem;

public class AudioInfo extends DataItem implements IDataResult {
    private String artist;
    private String title;
    private Integer duration;
    private String url;
    private Integer lyricsId;
    private Integer albumId;
    private AudioGenre genre;
    private Integer date;

    private String convertedDuration;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public AudioGenre getGenre() {
        return genre;
    }

    public void setGenre(AudioGenre genre) {
        this.genre = genre;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
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
