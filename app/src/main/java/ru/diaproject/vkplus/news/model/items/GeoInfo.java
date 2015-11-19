package ru.diaproject.vkplus.news.model.items;

public class GeoInfo {
    private Integer placeId;
    private String title;
    private String type;
    private String countryId;
    private Integer cityId;
    private String address;
    private String showMap;

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShowMap() {
        return showMap;
    }

    public void setShowMap(String showMap) {
        this.showMap = showMap;
    }
}
