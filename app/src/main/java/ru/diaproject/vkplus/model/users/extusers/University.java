package ru.diaproject.vkplus.model.users.extusers;

import org.json.JSONException;
import org.json.JSONObject;

import ru.diaproject.vkplus.model.DataObject;

public class University extends DataObject{
    public static final String JSON_COUNTRY_ID = "country";
    public static final String JSON_CITY_ID = "city";
    public static final String JSON_NAME = "name";

    public static final String JSON_FACULTY_ID = "faculty";
    public static final String JSON_FACULTY_NAME = "faculty_name";

    public static final String JSON_CHAIR_ID = "chair";
    public static final String JSON_CHAIR_NAME = "chair_name";

    public static final String JSON_GRADUATION = "graduation";

    public static University parseObject(JSONObject object){
        University university = new University();
        try {
            university.setCountryId(object.getInt(JSON_COUNTRY_ID));
            university.setCityId(object.getInt(JSON_CITY_ID));
            university.setName(object.getString(JSON_NAME));

            university.setFacultyId(object.getInt(JSON_FACULTY_ID));
            university.setFacultyName(object.getString(JSON_FACULTY_NAME));

            university.setChairId(object.getInt(JSON_CHAIR_ID));
            university.setChairName(object.getString(JSON_CHAIR_NAME));

            university.setGraduation(object.getInt(JSON_GRADUATION));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return university;
    }

    private Integer countryId;
    private Integer cityId;
    private String name;

    private Integer facultyId;
    private String facultyName;

    private Integer chairId;
    private String chairName;

    private Integer graduation;

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

    public Integer getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public Integer getChairId() {
        return chairId;
    }

    public void setChairId(Integer chairId) {
        this.chairId = chairId;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }

    public Integer getGraduation() {
        return graduation;
    }

    public void setGraduation(Integer graduation) {
        this.graduation = graduation;
    }
}
