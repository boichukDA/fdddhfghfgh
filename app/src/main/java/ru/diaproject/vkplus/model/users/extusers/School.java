package ru.diaproject.vkplus.model.users.extusers;


import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class School extends DataObject{
    public static final String JSON_COUNTRY_ID = "country";
    public static final String JSON_CITY_ID = "city";
    public static final String JSON_NAME = "name";

    public static final String JSON_YEAR_FROM = "year_from";
    public static final String JSON_YEAR_TO= "year_to";
    public static final String JSON_YEAR_GRADUATED = "year_graduated ";
    public static final String JSON_CLASS = "class";

    public static School parseObject(JSONObject object){
        School school = new School();
        try {
            school.setCountryId(object.getInt(JSON_COUNTRY_ID));
            school.setCityId(object.getInt(JSON_CITY_ID));
            school.setName(object.getString(JSON_NAME));

            school.setYearFrom(object.getInt(JSON_YEAR_FROM));
            school.setYearTo(object.getInt(JSON_YEAR_TO));
            school.setYearGraduated(object.optInt(JSON_YEAR_GRADUATED));
            school.setClss(object.getString(JSON_CLASS));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return school;
    }
    
    private Integer countryId;
    private Integer cityId;
    private String name;

    private Integer yearFrom;
    private Integer yearTo;
    private Integer yearGraduated;
    private String clss;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public Integer getYearGraduated() {
        return yearGraduated;
    }

    public void setYearGraduated(Integer yearGraduated) {
        this.yearGraduated = yearGraduated;
    }

    public String getClss() {
        return clss;
    }

    public void setClss(String clss) {
        this.clss = clss;
    }
}
