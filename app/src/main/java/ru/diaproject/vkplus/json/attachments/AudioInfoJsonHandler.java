package ru.diaproject.vkplus.json.attachments;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.attachments.audios.AudioGenre;
import ru.diaproject.vkplus.model.attachments.audios.AudioInfo;

public class AudioInfoJsonHandler {
    public AudioInfo parse(JSONObject jsonObject) throws JSONException {
        AudioInfo info = new AudioInfo();

        info.setId(jsonObject.getInt("id"));
        info.setOwnerId(jsonObject.getInt("owner_id"));
        info.setAlbumId(jsonObject.optInt("album_id", 0));
        info.setArtist(jsonObject.getString("artist"));

        info.setTitle(jsonObject.getString("title"));
        info.setDuration(jsonObject.optInt("duration", 0));
        info.setUrl(jsonObject.optString("url", ""));
        info.setLyricsId(jsonObject.optInt("lyrics_id", 0));

        info.setGenre(AudioGenre.valueOf(jsonObject.optInt("genre_id", 0)));
        info.setDate(jsonObject.getInt("date"));
        return info;
    }
}
