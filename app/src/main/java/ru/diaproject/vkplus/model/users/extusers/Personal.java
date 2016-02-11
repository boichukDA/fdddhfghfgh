package ru.diaproject.vkplus.model.users.extusers;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ru.diaproject.vkplus.model.DataObject;

public class Personal extends DataObject{
    public static final String JSON_POLITICAL = "political";
    public static final String JSON_LANGS = "langs";
    public static final String JSON_RELIGION = "religion";
    public static final String JSON_INSPIRED_BY = "inspired_by";
    public static final String JSON_PEOPLE_MAIN = "people_main";
    public static final String JSON_LIFE_MAIN = "life_main";
    public static final String JSON_SMOKING = "smoking";
    public static final String JSON_ALCOHOL = "alcohol";

    public static Personal parseObject(JSONObject object){
        Personal item = new Personal();

            item.setAlcohol(object.optInt(JSON_ALCOHOL));
            item.setInspiredBy(object.optString(JSON_INSPIRED_BY));
           // item.setLangs(object.optString(JSON_INSPIRED_BY));
            item.setLifeMain(object.optInt(JSON_LIFE_MAIN));
            item.setPeopleMain(object.optInt(JSON_PEOPLE_MAIN));
            item.setPolitical(object.optInt(JSON_POLITICAL));
            item.setReligion(object.optString(JSON_RELIGION));
            item.setSmoking(object.optInt(JSON_SMOKING));

        return item;
    }

    private Integer political;
    private List<String> langs;
    private String religion;
    private String inspiredBy;
    private Integer peopleMain;
    private Integer lifeMain;
    private Integer smoking;
    private Integer alcohol;

    public Integer getPolitical() {
        return political;
    }

    public void setPolitical(Integer political) {
        this.political = political;
    }

    public List<String> getLangs() {
        return langs;
    }

    public void setLangs(List<String> langs) {
        this.langs = langs;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getInspiredBy() {
        return inspiredBy;
    }

    public void setInspiredBy(String inspiredBy) {
        this.inspiredBy = inspiredBy;
    }

    public Integer getPeopleMain() {
        return peopleMain;
    }

    public void setPeopleMain(Integer peopleMain) {
        this.peopleMain = peopleMain;
    }

    public Integer getLifeMain() {
        return lifeMain;
    }

    public void setLifeMain(Integer lifeMain) {
        this.lifeMain = lifeMain;
    }

    public Integer getSmoking() {
        return smoking;
    }

    public void setSmoking(Integer smooking) {
        this.smoking = smooking;
    }

    public Integer getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Integer alcohol) {
        this.alcohol = alcohol;
    }
}
