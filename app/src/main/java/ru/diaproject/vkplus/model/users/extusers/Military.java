package ru.diaproject.vkplus.model.users.extusers;

import org.json.JSONObject;

public class Military {
    public static final String JSON_UNIT = "unit";
    public static final String JSON_UNIT_ID = "unit_id";
    public static final String JSON_COUNTRY_ID = "country_id";
    public static final String JSON_FROM = "from";
    public static final String JSON_UNTIL = "until";

    public static Military parseObject(JSONObject object){
        Military military = new Military();
        military.setUnit(object.optString(JSON_UNIT));
        military.setUnitId(object.optInt(JSON_UNIT_ID));
        military.setCountryId(object.optInt(JSON_COUNTRY_ID));
        military.setFrom(object.optInt(JSON_FROM));
        military.setUntil(object.optInt(JSON_UNTIL));
        return military;
    }
    
    private String unit;
    private Integer unitId;
    private Integer countryId;
    private Integer from;
    private Integer until;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
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
}
