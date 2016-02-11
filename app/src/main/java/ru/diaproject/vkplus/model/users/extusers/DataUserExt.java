package ru.diaproject.vkplus.model.users.extusers;


import android.text.Spannable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.diaproject.vkplus.model.users.DeactivatedType;
import ru.diaproject.vkplus.model.users.IDataUser;
import ru.diaproject.vkplus.model.users.NetworkStatus;
import ru.diaproject.vkplus.model.users.OwnerSex;
import ru.diaproject.vkplus.model.users.extusers.counters.Counters;
import ru.diaproject.vkplus.model.users.extusers.occupations.Occupation;
import ru.diaproject.vkplus.model.users.extusers.relations.RelationPartner;
import ru.diaproject.vkplus.model.users.extusers.relatives.RelativiesItem;

public class DataUserExt implements IDataUserExt{
    public static final String JSON_BDATE = "bdate";
    public static final String JSON_CITY = "city";
    public static final String JSON_HOME_TOWN = "home_town";

    public static final String JSON_COUNTRY = "country";
    public static final String JSON_PHOTO_200 = "photo_200";
    public static final String JSON_PHOTO_MAX = "photo_max";
    public static final String JSON_PHOTO_MAX_ORIG = "photo_max_orig";
    public static final String JSON_PHOTO_ID = "photo_id";

    public static final String JSON_HAS_PHOTO = "has_photo";
    public static final String JSON_IS_FRIEND = "is_friend";
    public static final String JSON_FRIEND_STATUS = "friend_status";

    public static final String JSON_MOBILE_PHONE = "mobile_phone";
    public static final String JSON_HOME_PHONE = "home_phone";

    public static final String JSON_CONTACTS = "contacts";
    public static final String JSON_SITE = "site";
    public static final String JSON_UNIVERSITIES = "universities";
    public static final String JSON_SCHOOLS = "schools";

    public static final String JSON_STATUS = "status";
    public static final String JSON_COMMON_COUNT = "common_count";
    public static final String JSON_NICKNAME = "nickname";
    public static final String JSON_RELATIVIES = "relatives";

    public static final String JSON_RELATION = "relation";
    public static final String JSON_RELATION_PARTNER = "relation_partner";
    public static final String JSON_PERSONAL = "personal";
    public static final String JSON_WALL_COMMENTS = "wall_comments";

    public static final String JSON_OCCUPATION = "occupation";
    public static final String JSON_ACTIVITIES = "activities";
    public static final String JSON_INTERESTS = "interests";
    public static final String JSON_MUSIC = "music";

    public static final String JSON_MOVIES = "movies";
    public static final String JSON_TV = "tv";
    public static final String JSON_BOOKS = "books";

    public static final String JSON_GAMES = "games";
    public static final String JSON_ABOUT = "about";
    public static final String JSON_QUOTES = "quotes";

    public static final String JSON_CAN_POST = "can_post";
    public static final String JSON_SEE_AUDIO = " can_see_audio";
    public static final String JSON_SEE_ALL_POSTS = "can_see_all_posts";

    public static final String JSON_WRITE_MESSAGE = "can_write_private_message";
    public static final String JSON_FRIEND_REQUEST = "can_send_friend_request";
    public static final String JSON_MAINDEN_NAME = "maiden_name";

    public static final String JSON_CAREER = "career";
    public static final String JSON_MILITARY = "military";
    public static final String JSON_COUNTERS = "counters";

    public static DataUserExt parseObject(JSONObject object) {
        DataUserExt user = new DataUserExt();
        try {
            JSONArray respObject = object.getJSONArray("response");
            object = respObject.getJSONObject(0);
            System.out.print(respObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
         //UserJsonHandler handler = new UserJsonHandler();
        try {
           // IDataUser dataUser = handler.parse(object);
            //user.setUser(dataUser);

            user.setBdate(object.optString(JSON_BDATE));
            JSONObject city = object.optJSONObject(JSON_CITY);
            if (city != null)
                user.setCity(City.parseObject(city));

            user.setHomeTown(object.optString(JSON_HOME_TOWN, ""));

            JSONObject country = object.optJSONObject(JSON_COUNTRY);
            if (country != null)
                user.setCountry(Country.parseObject(country));

            user.setPhoto200(object.optString(JSON_PHOTO_200));
            user.setPhotoMax(object.optString(JSON_PHOTO_MAX));
            user.setPhotoMaxOrig(object.optString(JSON_PHOTO_MAX_ORIG));
            user.setPhotoId(object.optString(JSON_PHOTO_ID));

            user.setHasPhoto(object.optInt(JSON_HAS_PHOTO) > 0);
            user.setIsFriend(object.optInt(JSON_IS_FRIEND) > 0);
            user.setFriendStatus(object.optInt(JSON_FRIEND_STATUS));

            user.setHomePhone(object.optString(JSON_HOME_PHONE));
            user.setMobilePhone(object.optString(JSON_MOBILE_PHONE));

            JSONObject contacts = object.optJSONObject(JSON_CONTACTS);
            if (contacts != null)
                user.setContacts(Contacts.parseObject(contacts));

            user.setSite(object.optString(JSON_SITE));

            JSONArray universities = object.optJSONArray(JSON_UNIVERSITIES);
            if (universities != null) {
                List<University> universityList = new ArrayList<>();
                for (int index = 0; index < universities.length(); index++)
                    universityList.add(University.parseObject(universities.getJSONObject(index)));
                user.setUniversities(universityList);
            }

            JSONArray schools = object.optJSONArray(JSON_SCHOOLS);
            if (schools != null) {
                List<School> schoolList = new ArrayList<>();
                for (int index = 0; index < schools.length(); index++)
                    schoolList.add(School.parseObject(schools.getJSONObject(index)));
                user.setSchools(schoolList);
            }

            // user.setStatus(object.optString(JSON_STATUS));
            user.setCommonCount(object.optInt(JSON_COMMON_COUNT));
            user.setNickname(object.optString(JSON_NICKNAME));

            JSONArray relativies = object.optJSONArray(JSON_RELATIVIES);
            if (relativies != null) {
                List<RelativiesItem> items = new ArrayList<>();
                for (int index = 0; index < relativies.length(); index++)
                    items.add(RelativiesItem.parseObject(relativies.getJSONObject(index)));

                user.setRelativies(items);
            }

            user.setRelation(object.optInt(JSON_RELATION));

            JSONObject partner = object.optJSONObject(JSON_RELATION_PARTNER);
            if (partner != null)
                user.setRelationPartner(RelationPartner.parseObject(partner));

            JSONObject personal = object.optJSONObject(JSON_PERSONAL);
            if (personal != null)
                user.setPersonal(Personal.parseObject(personal));

            JSONObject occupation = object.optJSONObject(JSON_OCCUPATION);
            if (occupation != null)
                user.setOccupation(Occupation.parseObject(occupation));
            user.setWallComments(object.optInt(JSON_WALL_COMMENTS) > 0);

            user.setActivities(object.optString(JSON_ACTIVITIES));
            user.setInterests(object.optString(JSON_INTERESTS));
            user.setMusic(object.optString(JSON_MUSIC));

            user.setMovies(object.optString(JSON_MOVIES));
            user.setTv(object.optString(JSON_TV));
            user.setBooks(object.optString(JSON_BOOKS));

            user.setGames(object.optString(JSON_GAMES));
            user.setAbout(object.optString(JSON_ABOUT));
            user.setQuotes(object.optString(JSON_QUOTES));

            user.setCanPost(object.optInt(JSON_CAN_POST) > 0);
            user.setCanSeeAudio(object.optInt(JSON_SEE_AUDIO) > 0);
            user.setCanSeePost(object.optInt(JSON_SEE_ALL_POSTS) > 0);

            user.setCanWritePrivateMessage(object.optInt(JSON_WRITE_MESSAGE) > 0);
            user.setFriendRequest(object.optInt(JSON_FRIEND_REQUEST) > 0);
            user.setMaidenName(object.optString(JSON_MAINDEN_NAME));

            JSONArray careers = object.optJSONArray(JSON_CAREER);
            if (careers != null) {
                List<Career> careerList = new ArrayList<>();
                for (int index = 0; index < careers.length(); index++)
                    careerList.add(Career.parseObject(careers.getJSONObject(index)));
                user.setCareers(careerList);
            }

            JSONArray military = object.optJSONArray(JSON_MILITARY);
            if (military != null) {
                List<Military> militaryList = new ArrayList<>();
                for (int index = 0; index < military.length(); index++)
                    militaryList.add(Military.parseObject(military.getJSONObject(index)));
                user.setMilitaries(militaryList);
            }

            JSONObject counters = object.optJSONObject(JSON_COUNTERS);
            if (counters != null)
                user.setCounters(Counters.parseObject(counters));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
    private IDataUser user;

    private String bdate;
    private City city;
    private String homeTown;

    private Country country;
    private String photo200;
    private String photoMax;
    private String photoMaxOrig;
    private String photoId;

    private boolean isFriend;
    private Integer friendStatus;

    private boolean canWritePrivateMessage;
    private boolean friendRequest;

    private String mobilePhone;
    private String homePhone;
    private boolean hasPhoto;

    private Contacts contacts;
    private String site;

    private List<University> universities;
    private List<School> schools;

    private Integer commonCount;
    private String nickname;

    private List<RelativiesItem> relativies;

    private Integer relation;
    private RelationPartner relationPartner;

    private Personal personal;
    private boolean wallComments;

    private String activities;
    private String interests;
    private String music;

    private String movies;
    private String tv;
    private String books;

    private String games;
    private String about;
    private String quotes;

    private boolean canPost;
    private boolean canSeePost;
    private boolean canSeeAudio;

    private boolean canSendMessage;
    private boolean canFriendRequest;
    private String maidenName;

    private List<Career> careers;
    private List<Military> militaries;
    private Counters counters;
    private Occupation occupation;
    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public DeactivatedType getType() {
        return user.getType();
    }

    @Override
    public boolean isHidden() {
        return user.isHidden();
    }

    @Override
    public String getScreenName() {
        return user.getScreenName();
    }

    @Override
    public NetworkStatus getNetworkStatus() {
        return user.getNetworkStatus();
    }

    @Override
    public String getStatus() {
        return user.getStatus();
    }

    @Override
    public Spannable getFullName() {
        return user.getFullName();
    }

    @Override
    public OwnerSex getSex() {
        return user.getSex();
    }

    @Override
    public Integer getPlaceholderResource() {
        return user.getPlaceholderResource();
    }

    @Override
    public Integer getErrorResource() {
        return user.getErrorResource();
    }

    @Override
    public String getPhoto100() {
        return user.getPhoto100();
    }

    @Override
    public Integer getId() {
        return user.getId();
    }

    public IDataUser getUser() {
        return user;
    }

    public void setUser(IDataUser user) {
        this.user = user;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPhoto200() {
        return photo200;
    }

    public void setPhoto200(String photo200) {
        this.photo200 = photo200;
    }

    public String getPhotoMax() {
        return photoMax;
    }

    public void setPhotoMax(String photoMax) {
        this.photoMax = photoMax;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
    }

    public boolean isCanWritePrivateMessage() {
        return canWritePrivateMessage;
    }

    public void setCanWritePrivateMessage(boolean canWritePrivateMessage) {
        this.canWritePrivateMessage = canWritePrivateMessage;
    }

    public boolean isFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(boolean friendRequest) {
        this.friendRequest = friendRequest;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<University> getUniversities() {
        return universities;
    }

    public void setUniversities(List<University> universities) {
        this.universities = universities;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    public Integer getCommonCount() {
        return commonCount;
    }

    public void setCommonCount(Integer commonCount) {
        this.commonCount = commonCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<RelativiesItem> getRelativies() {
        return relativies;
    }

    public void setRelativies(List<RelativiesItem> relativies) {
        this.relativies = relativies;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public RelationPartner getRelationPartner() {
        return relationPartner;
    }

    public void setRelationPartner(RelationPartner relationPartner) {
        this.relationPartner = relationPartner;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public boolean isWallComments() {
        return wallComments;
    }

    public void setWallComments(boolean wallComments) {
        this.wallComments = wallComments;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getMovies() {
        return movies;
    }

    public void setMovies(String movies) {
        this.movies = movies;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getGames() {
        return games;
    }

    public void setGames(String games) {
        this.games = games;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public boolean isCanPost() {
        return canPost;
    }

    public void setCanPost(boolean canPost) {
        this.canPost = canPost;
    }

    public boolean isCanSeePost() {
        return canSeePost;
    }

    public void setCanSeePost(boolean canSeePost) {
        this.canSeePost = canSeePost;
    }

    public boolean isCanSeeAudio() {
        return canSeeAudio;
    }

    public void setCanSeeAudio(boolean canSeeAudio) {
        this.canSeeAudio = canSeeAudio;
    }

    public boolean isCanSendMessage() {
        return canSendMessage;
    }

    public void setCanSendMessage(boolean canSendMessage) {
        this.canSendMessage = canSendMessage;
    }

    public boolean isCanFriendRequest() {
        return canFriendRequest;
    }

    public void setCanFriendRequest(boolean canFriendRequest) {
        this.canFriendRequest = canFriendRequest;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public List<Career> getCareers() {
        return careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    public List<Military> getMilitaries() {
        return militaries;
    }

    public void setMilitaries(List<Military> militaries) {
        this.militaries = militaries;
    }

    public Counters getCounters() {
        return counters;
    }

    public void setCounters(Counters counters) {
        this.counters = counters;
    }

    public void setPhotoMaxOrig(String photoMaxOrig) {
        this.photoMaxOrig = photoMaxOrig;
    }

    public String getPhotoMaxOrig() {
        return photoMaxOrig;
    }

    public String getStringFullName(){
        return user.getStringFullName();
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
