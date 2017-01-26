package com.optimussoftware.generator.db;


import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

/**
 * Created by leonardojpr on 23/08/16.
 */
public class TableDefinition {

    public static void addCommons(Entity model) {
        model.setCodeBeforeClass("@SuppressWarnings(\"ALL\")");
        model.implementsSerializable();
        model.addStringProperty("_id").notNull().unique().primaryKey();
        model.addStringProperty("_etag");
        model.addDateProperty("created");
        model.addDateProperty("updated");
        model.addBooleanProperty("active");
        model.addBooleanProperty("deleted");
    }

    public static Entity addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        addCommons(user);
        user.addStringProperty("social_id");
        user.addStringProperty("password");
        user.addStringProperty("email").notNull();
        user.addStringProperty("first_name");
        user.addStringProperty("middle_name");
        user.addStringProperty("last_name");
        user.addStringProperty("full_name");
        user.addStringProperty("phone");
        user.addStringProperty("location");
        user.addStringProperty("about_me");
        user.addStringProperty("gender");
        user.addIntProperty("timezone");
        user.addStringProperty("link");
        user.addStringProperty("source");
        user.addStringProperty("profile_photo");
        user.addDateProperty("birthday");
        return user;
    }

    /**
     * Create location's Properties
     *
     * @return Location entity
     */
    public static Entity addLocation(Schema schema) {
        Entity location = schema.addEntity("Location");
        addCommons(location);
        location.addStringProperty("name");
        location.addStringProperty("city");
        location.addStringProperty("state");
        location.addStringProperty("country");
        location.addStringProperty("home_page");
        location.addStringProperty("telephone");
        location.addStringProperty("zip");
        location.addStringProperty("image");

        return location;
    }

    /**
     * Create location's Properties
     *
     * @return Location entity
     */
    public static Entity addCampaign(Schema schema) {
        Entity campaign = schema.addEntity("Campaign");
        addCommons(campaign);
        campaign.addStringProperty("name");
        campaign.addDateProperty("finish_date");
        campaign.addDateProperty("start_date");
        campaign.addIntProperty("start_hour");
        campaign.addIntProperty("end_hour");
        campaign.addIntProperty("max_age");
        campaign.addStringProperty("efectiveDays");
        return campaign;
    }

    /**
     * Create advertising's Properties
     *
     * @return Advertising entity
     */
    public static Entity addAdvertising(Schema schema) {
        Entity advertising = schema.addEntity("Advertising");
        addCommons(advertising);
        advertising.addStringProperty("name");
        advertising.addDoubleProperty("price");
        advertising.addStringProperty("description");
        advertising.addBooleanProperty("is_homepage");
        advertising.addIntProperty("count_likes");
        advertising.addIntProperty("count_dislikes");
        advertising.addIntProperty("count_votes");
        advertising.addIntProperty("acum_votes");
        advertising.addStringProperty("location_id");
        return advertising;
    }

    /**
     * Create image's Properties
     *
     * @return Image entity
     */
    public static Entity addImage(Schema schema) {
        Entity image = schema.addEntity("Image");
        addCommons(image);
        image.addStringProperty("name");
        image.addStringProperty("file");
        image.addIntProperty("width");
        image.addIntProperty("height");
        image.addIntProperty("position");
        return image;
    }

    /**
     * Create interest's Properties
     *
     * @return Interst entity
     */
    public static Entity addInterest(Schema schema) {
        Entity interest = schema.addEntity("Interest");
        addCommons(interest);
        interest.addStringProperty("name");
        interest.addStringProperty("image");
        interest.addStringProperty("description");
        // Campaign --> False || Advertising --> true
        interest.addBooleanProperty("indicator");
        return interest;
    }

    /**
     * Create interest's Properties
     *
     * @return Interst entity
     */
    public static Entity addUserInterest(Schema schema) {
        Entity userInterest = schema.addEntity("UserInterest");
        userInterest.addIdProperty().autoincrement().primaryKey();
        userInterest.addStringProperty("interest_id");
        return userInterest;
    }

    /**
     * Create review for advertising Properties
     *
     * @return Review entity
     */
    public static Entity addReview(Schema schema) {
        Entity review = schema.addEntity("Review");
        addCommons(review);
        review.addIntProperty("vote");
        review.addStringProperty("comment");
        review.addStringProperty("lang");
        review.addStringProperty("first_name");
        review.addStringProperty("last_name");
        review.addStringProperty("profile_photo");
        return review;
    }

    /**
     * Create user's likes Properties
     *
     * @return Likes entity
     */
    public static Entity addLikes(Schema schema) {
        Entity likes = schema.addEntity("Likes");
        addCommons(likes);
        likes.addStringProperty("advertising_id");
        return likes;
    }

    /**
     * Create user's dislikes Properties
     *
     * @return Dislikes entity
     */
    public static Entity addDislikes(Schema schema) {
        Entity dislikes = schema.addEntity("Dislikes");
        addCommons(dislikes);
        dislikes.addStringProperty("advertising_id");
        return dislikes;
    }

    /**
     * Create user's favorites Properties
     *
     * @return Favorites entity
     */
    public static Entity addFavorites(Schema schema) {
        Entity favorites = schema.addEntity("Favorites");
        addCommons(favorites);
        favorites.addStringProperty("advertising_id");
        return favorites;
    }

    /**
     * Create user's visit Properties
     *
     * @return Visit entity
     */
    public static Entity addVisit(Schema schema) {
        Entity visit = schema.addEntity("Visit");
        addCommons(visit);
        visit.addStringProperty("advertising_id");
        return visit;
    }

    /**
     * Create user's reseach Properties
     *
     * @return Notification entity
     */
    public static Entity addNotification(Schema schema) {
        Entity notification = schema.addEntity("Notification");
        addCommons(notification);
        notification.addBooleanProperty("read");
        notification.addBooleanProperty("block");
        return notification;
    }

    /**
     * Create user's Devices Properties
     *
     * @return Notification entity
     */
    public static Entity addDevices(Schema schema) {
        Entity devices = schema.addEntity("Devices");
        addCommons(devices);
        devices.addStringProperty("name_key");
        devices.addStringProperty("description");
        devices.addStringProperty("uuid");
        devices.addStringProperty("mac");
        devices.addIntProperty("major");
        devices.addIntProperty("minor");
        devices.addIntProperty("rssi");
        devices.addIntProperty("namespace");
        devices.addIntProperty("instance");
        devices.addIntProperty("type");
        devices.addStringProperty("url");
        devices.addStringProperty("message_for_user");
        devices.addBooleanProperty("is_single");
        return devices;
    }

    /**
     * Create user's Devices Properties
     *
     * @return Notification entity
     */
    public static Entity addAdvertisingImage(Schema schema) {
        Entity advertisingImage = schema.addEntity("AdvertisingImage");
        advertisingImage.addIdProperty().unique().autoincrement().primaryKey();
        advertisingImage.addStringProperty("advertising_id");
        advertisingImage.addStringProperty("image_id");

        return advertisingImage;
    }

    public static Entity addAdvertisinginterest(Schema schema) {
        Entity advertisingInterest = schema.addEntity("AdvertisingInterest");
        advertisingInterest.addIdProperty().unique().autoincrement().primaryKey();
        advertisingInterest.addStringProperty("advertising_id");
        advertisingInterest.addStringProperty("interest_id");

        return advertisingInterest;
    }

    public static Entity addNotificationAdvertising(Schema schema) {
        Entity notificationAdvertising = schema.addEntity("NotificationAdvertising");
        notificationAdvertising.setCodeBeforeClass("@SuppressWarnings(\"ALL\")");
        notificationAdvertising.implementsSerializable();
        notificationAdvertising.addStringProperty("_id").notNull().unique().primaryKey();
        notificationAdvertising.addDateProperty("date");
        notificationAdvertising.addBooleanProperty("viewed");
        notificationAdvertising.addBooleanProperty("delete");

        return notificationAdvertising;
    }

}
