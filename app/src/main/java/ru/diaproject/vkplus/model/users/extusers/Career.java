package ru.diaproject.vkplus.model.users.extusers;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class Career extends DataObject {
    public static final String JSON_GROUP_ID = "group_id";
    public static final String JSON_COMPANY = "company";
    public static final String JSON_COUNTRY_ID = "country_id";
    public static final String JSON_CITY_ID = "city_id";
    public static final String JSON_CITY_NAME = "city_name";
    public static final String JSON_FROM = "from";
    public static final String JSON_UNTIL = "until";
    public static final String JSON_POSITION = "position";

    private Integer groupId;
    private String company;
    private Integer countryId;
    private Integer cityId;
    private String cityName;
    private Integer from;
    private Integer until;
    private String position;

    public static Career parseObject(JSONObject object){
        Career career = new Career();
        career.setGroupId(object.optInt(JSON_GROUP_ID));
        career.setCompany(object.optString(JSON_COMPANY));
        career.setCountryId(object.optInt(JSON_COUNTRY_ID));
        career.setCityId(object.optInt(JSON_CITY_ID));
        career.setCityName(object.optString(JSON_CITY_NAME));
        career.setFrom(object.optInt(JSON_FROM));
        career.setUntil(object.optInt(JSON_UNTIL));
        career.setPosition(object.optString(JSON_POSITION));
        return career;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getUntil() {
        return until;
    }

    public void setUntil(Integer until) {
        this.until = until;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
