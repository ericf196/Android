package com.optimussoftware.api.core;

import android.util.Log;

import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;
import com.optimussoftware.api.response.newAdvertising.Source;
import com.optimussoftware.api.response.review.ReviewCheck.ReviewCheck;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.Campaign;
import com.optimussoftware.db.Favorites;
import com.optimussoftware.db.Image;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.Location;
import com.optimussoftware.db.Review;
import com.optimussoftware.db.User;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.boohos.data.Constants;
import com.optimussoftware.boohos.util.Commons;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Created by william.castillo@optimus-software.com on 10/06/16.
 * Change response to object
 */
public class Factory {

    private static final String TAG = Factory.class.getSimpleName();

    /**
     * @param gender
     * @return
     */
    private static String getGenderGoogle(int gender) {
        switch (gender) {
            case Person.Gender.FEMALE:
                return "female";
            case Person.Gender.MALE:
                return "male";
            default:
                return "other";
        }
    }

    /**
     * @param response
     * @param numberPhone
     * @return
     * @throws JSONException
     */
    public static User getUserFromFacebookData(GraphResponse response, String numberPhone) throws JSONException {
        User user = new User();
        JSONObject object = response.getJSONObject();
        String url_pic = "https://graph.facebook.com/" + object.get("id") + "/picture?type=large";
        if (numberPhone != null)
            user.setPhone(numberPhone);
        user.setSource(Constants.SOURCE_FACEBOOK);
        user.setProfile_photo(url_pic);
        user.setSocial_id(object.getString("id"));
        user.setFull_name(object.getString("name"));
        if (object.has("first_name"))
            user.setFirst_name(object.getString("first_name"));
        if (object.has("middle_name"))
            user.setMiddle_name(object.getString("middle_name"));
        if (object.has("last_name"))
            user.setLast_name(object.getString("last_name"));
        if (object.has("gender"))
            user.setGender(object.getString("gender"));
        if (object.has("email"))
            user.setEmail(object.getString("email"));
        if (object.has("link"))
            user.setLink(object.getString("link"));
        if (object.has("birthday")) {
            user.setBirthday(Commons.parseDateFacebook((String) object.get("birthday")));
        }
        if (object.has("hometown")) {
            JSONObject hometown = object.getJSONObject("hometown");
            user.setLocation(hometown.getString("name"));
        }
        return user;
    }

    /**
     * @param acct
     * @param person
     * @param numberPhone
     * @return
     */
    public static User getUserFromGoogleData(GoogleSignInAccount acct, Person person, String numberPhone) {
        User user = new User();
        if (numberPhone != null)
            user.setPhone(numberPhone);
        user.setSource(Constants.SOURCE_GOOGLE_PLUS);
        user.setSocial_id(acct.getId());
        user.setEmail(acct.getEmail());
        if (acct.getPhotoUrl() != null)
            user.setProfile_photo(acct.getPhotoUrl().toString());
        if (person != null) {
            user.setFull_name(person.getDisplayName());
            if (person.hasGender()) {
                user.setGender(getGenderGoogle(person.getGender()));
            }
            if (person.hasAboutMe())
                user.setAbout_me(person.getAboutMe());
            if (person.hasUrl())
                user.setLink(person.getUrl());
            if (person.hasBirthday())
                user.setBirthday(Commons.parseDate(person.getBirthday()));

            Person.Name name = person.getName();
            if (name.hasFamilyName())
                user.setLast_name(name.getFamilyName());
            if (name.hasGivenName())
                user.setFirst_name(name.getGivenName());
            if (name.hasMiddleName())
                user.setMiddle_name(name.getMiddleName());

            user.setLocation(person.getCurrentLocation());

        }
        return user;
    }

    public static User getUserFromResponse(com.optimussoftware.api.response.user.User item) {
        User obj = new User();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        /*obj.setCreated(Commons.parseDate(item.getCreated()));
        obj.setUpdated(Commons.parseDate(item.getUpdated())); */
        obj.setFirst_name(item.getFirstName());
        obj.setLast_name(item.getLastName());
        obj.setFull_name(item.getFullName());
        obj.setEmail(item.getEmail());
        obj.setPhone(item.getPhone());
        obj.setBirthday(item.getBirthday());
        obj.setGender(item.getGender());
        obj.setSource(item.getSource());

        if (item.getSource().equals(Constants.SOURCE_SELF)) {
            try {
                obj.setProfile_photo(Constants.URL_WEB + item.getProfilePhoto().getFilename());
            } catch (Exception e) {
                obj.setProfile_photo("");
            }
        } else {
            if (item.getProfilePhoto() != null) {
                obj.setProfile_photo(Constants.URL_WEB + item.getProfilePhoto().getFilename());
            } else {
                obj.setProfile_photo(item.getSocialPhoto());
            }

        }
        return obj;
    }

    public static Location getLocationSource(Source item) {
        Location obj = new Location();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setName(item.getName());
        obj.setImage(item.getLogo());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Advertising getAdvertisingFromResponse(com.optimussoftware.api.response.newAdvertising.Item item) {
        Advertising obj = new Advertising();
        obj.setActive(true);
        obj.setDeleted(false);
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setCreated(Commons.parseDate(item.getCreated()));
        obj.setUpdated(Commons.parseDate(item.getUpdated()));
        obj.setDescription(item.getDescription());
        obj.setName(item.getName());
        obj.setCount_votes(item.getCountVotes());
        obj.setAcum_votes(item.getAcumVotes());
        obj.setCount_dislikes(item.getCountDislikes());
        obj.setCount_likes(item.getCountLikes());
        obj.setCampaign_id(item.getCampaignId().getId());
        obj.setLocation_id(item.getSource().getId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Advertising getAdvertisingFromResponse(com.optimussoftware.api.response.advertising.AdvertisingByCampaign.Item item) {
        Advertising obj = new Advertising();
        obj.setActive(true);
        obj.setDeleted(false);
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setDescription(item.getDescription());
        obj.setName(item.getName());
        obj.setCount_votes(item.getCountVotes());
        obj.setAcum_votes(item.getAcumVotes());
        obj.setCount_dislikes(item.getCountDislikes());
        obj.setCount_likes(item.getCountLikes());
        obj.setCampaign_id(item.getCampaignId());
        //obj.setLocation_id(item.getLocationId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Advertising getAdvertisingFavoriteFromResponse(com.optimussoftware.api.response.favorites.advertisingFavorite.AdvertisingId item) {
        Advertising obj = new Advertising();
        obj.setActive(true);
        obj.setDeleted(false);
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setDescription(item.getDescription());
        Log.e("Favorite", "item.getName()-> " + item.getName());
        obj.setName(item.getName());
        obj.setCount_votes(item.getCountVotes());
        obj.setAcum_votes(item.getAcumVotes());
        obj.setCount_dislikes(item.getCountDislikes());
        obj.setCount_likes(item.getCountLikes());
        obj.setCampaign_id(item.getCampaignId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Interest getInterestFromResponse(com.optimussoftware.api.response.interest.Item item) {
        Interest obj = new Interest();
        obj.setName(item.getName());
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setActive(item.getActive());
        obj.setDeleted(item.getDelete());
        obj.setImage(item.getLogo().getFilename());
        obj.setDescription(item.getDescription());
        return obj;
    }

    public static Interest getInterestFromResponse(com.optimussoftware.api.response.newAdvertising.InterestList item) {
        Interest obj = new Interest();
        obj.setName(item.getName());
        obj.set_id(item.getId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static UserInterest getUserInterestFromResponse(com.optimussoftware.api.response.userinterest.Item item) {
        UserInterest obj = new UserInterest();
        obj.setUser_id(item.getUserId());
        obj.setInterest_id(item.getInterestId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static UserInterest getUserInterestFromWish(com.optimussoftware.api.response.user.Wish item, String user_id) {
        UserInterest obj = new UserInterest();
        obj.setUser_id(user_id);
        obj.setInterest_id(item.getId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Review getReviewFromResponse(com.optimussoftware.api.response.review.NewReview.Item item) {
        Review obj = new Review();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setCreated(Commons.parseDate(item.getCreated()));
        obj.setUpdated(Commons.parseDate(item.getUpdated()));
        obj.setVote(item.getVote());
        obj.setComment(item.getComment());
        obj.setLang(item.getLang());
        obj.setAdvertising_id(item.getAdvertisingId());
        obj.setFirst_name(item.getUserId().getFirstName());
        obj.setLast_name(item.getUserId().getLastName());
        if (item.getUserId().getSource().equals(Constants.SOURCE_SELF)) {
            obj.setProfile_photo(Constants.URL_WEB + item.getUserId().getProfilePhoto().getFilename());
        } else {
            if (item.getUserId().getProfilePhoto() != null) {
                obj.setProfile_photo(Constants.URL_WEB + item.getUserId().getProfilePhoto().getFilename());
            } else {
                obj.setProfile_photo(item.getUserId().getSocialPhoto());
            }
        }
        obj.setUser_id(item.getUserId().getId());
        return obj;
    }

    /**
     * @param reviewCheck
     * @return
     */
    public static Review getReviewFromCheck(String firts_name, String last_name, String profile_photo, ReviewCheck reviewCheck) {
        Review obj = new Review();
        obj.set_id(reviewCheck.getId());
        obj.set_etag(reviewCheck.getEtag());
        obj.setCreated(Commons.parseDate(reviewCheck.getCreated()));
        obj.setUpdated(Commons.parseDate(reviewCheck.getUpdated()));
        obj.setVote(reviewCheck.getVote());
        obj.setComment(reviewCheck.getComment());
        obj.setLang(reviewCheck.getLang());
        obj.setAdvertising_id(reviewCheck.getAdvertisingId());
        obj.setFirst_name(firts_name);
        obj.setLast_name(last_name);
        obj.setProfile_photo(profile_photo);
        obj.setUser_id(reviewCheck.getUserId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Favorites getFavoriteFromResponse(com.optimussoftware.api.response.favorites.Item item) {
        Favorites obj = new Favorites();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setUser_id(item.getUserId());
        obj.setAdvertising_id(item.getAdvertisingId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Favorites getFavoriteFromResponse(com.optimussoftware.api.response.favorites.advertisingFavorite.Item item) {
        Favorites obj = new Favorites();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setUser_id(item.getUserId());
        obj.setAdvertising_id(item.getAdvertisingId().getId());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Image getImageFromResponse(com.optimussoftware.api.response.commons.Image item, String advertising_id) {
        Image obj = new Image();
        obj.set_id(item.getId());
        obj.setAdvertising_id(advertising_id);
        obj.setFile(item.getFilename());
        obj.setHeight(item.getHeight());
        obj.setWidth(item.getWidth());
        return obj;
    }

    /**
     * @param item
     * @return
     */
    public static Image getImageFromResponse(com.optimussoftware.api.response.advertising.AdvertisingByCampaign.Image item, String advertising_id) {
        Image obj = new Image();
        obj.set_id(item.getId());
        obj.setAdvertising_id(advertising_id);
        obj.setFile(item.getFile().getFilename());
        obj.setHeight(item.getHeight());
        obj.setWidth(item.getWidth());
        return obj;
    }

    public static Campaign getCampaignFromResponse(com.optimussoftware.api.response.campaign.Item item) {
        Campaign obj = new Campaign();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setStart_date(Commons.parseDate(item.getStartDate()));
        obj.setFinish_date(Commons.parseDate(item.getFinishDate()));
        obj.setStart_hour(item.getStartHour());
        obj.setEnd_hour(item.getEndHour());
        obj.setName(item.getName());
        obj.setEfectiveDays(Commons.getEfectiveDaysCampaign(item.getDaysEfective()));
        return obj;
    }

    public static Campaign getCampaignFromResponse(com.optimussoftware.api.response.newAdvertising.CampaignId item) {
        Campaign obj = new Campaign();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setStart_date(Commons.parseDate(item.getStartDate()));
        obj.setFinish_date(Commons.parseDate(item.getFinishDate()));
        obj.setStart_hour(item.getStartHour());
        obj.setEnd_hour(item.getEndHour());
        obj.setName(item.getName());
        obj.setEfectiveDays(Commons.getEfectiveDaysCampaign(item.getDaysEfective()));
        return obj;
    }

    public static Location getLocationFromResponse(com.optimussoftware.api.response.location.Item item) {
        Location obj = new Location();
        obj.set_id(item.getId());
        obj.set_etag(item.getEtag());
        obj.setName(item.getName());
        obj.setImage(item.getLogo().getFilename());
        return obj;
    }


}
