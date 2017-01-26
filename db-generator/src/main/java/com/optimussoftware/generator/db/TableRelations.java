package com.optimussoftware.generator.db;


import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;

/**
 * Created by leonardojpr on 23/08/16.
 */
public class TableRelations {

    private static void makeRelationsOneToMany(Entity master, Entity slave, Property field, String titleMaster, String titleSlave) {
        slave.addToOne(master, field, titleSlave);
        master.addToMany(slave, field, titleMaster);
    }

    public static void advertisingImage(Entity advertising, Entity image) {
        Property relation = image.addStringProperty("advertising_id").notNull().getProperty();
        makeRelationsOneToMany(advertising, image, relation, "theAdvertisingImage", "images");
    }

    public static void userUsrInterest(Entity user, Entity usrInterst) {
        Property relation = usrInterst.addStringProperty("user_id").notNull().getProperty();
        makeRelationsOneToMany(user, usrInterst, relation, "wish", "myInterests");
    }

    public static void userLike(Entity user, Entity like) {
        Property relation = like.addStringProperty("user_id").notNull().getProperty();
        makeRelationsOneToMany(user, like, relation, "TheUserLike", "myLikes");
    }

    public static void userDislike(Entity user, Entity dislike) {
        Property relation = dislike.addStringProperty("user_id").notNull().getProperty();
        makeRelationsOneToMany(user, dislike, relation, "TheUserDislike", "myDislikes");
    }

    public static void userFavorite(Entity user, Entity favorite) {
        Property relation = favorite.addStringProperty("user_id").notNull().getProperty();
        makeRelationsOneToMany(user, favorite, relation, "TheUserFavorite", "myFavorites");
    }

    public static void locationFavorite(Entity location, Entity favorite) {
        Property relation = favorite.addStringProperty("location_id").getProperty();
        makeRelationsOneToMany(location, favorite, relation, "TheLocationFavorite", "myFavorite");
    }

    public static void userVisit(Entity user, Entity visit) {
        Property relation = visit.addStringProperty("user_id").notNull().getProperty();
        makeRelationsOneToMany(user, visit, relation, "TheUserVisit", "myVisits");
    }

    public static void advertisingNotification(Entity advertising, Entity notificacion) {
        Property relation = notificacion.addStringProperty("advertising_id").notNull().getProperty();
        makeRelationsOneToMany(advertising, notificacion, relation, "theAdvertisingNotifi", "theNotifi");
    }

    public static void notificationAdvertising(Entity advertising, Entity notificacion) {
        Property relation = notificacion.addStringProperty("advertising_id").notNull().getProperty();
        makeRelationsOneToMany(advertising, notificacion, relation, "theNotifiAdvertising", "TheNotifiA");
    }

    public static void notificationCampaign(Entity campaign, Entity notificacion) {
        Property relation = notificacion.addStringProperty("campaign_id").notNull().getProperty();
        makeRelationsOneToMany(campaign, notificacion, relation, "theNotifiCampaign", "TheNotifiC");
    }

    public static void campaignAdvertising(Entity campaign, Entity advertising) {
        Property relation = advertising.addStringProperty("campaign_id").getProperty();
        makeRelationsOneToMany(campaign, advertising, relation, "theCampaignAdvertising", "TheAvertisings");
    }

    public static void advertisingReview(Entity advertising, Entity review) {
        Property relation = review.addStringProperty("advertising_id").notNull().getProperty();
        makeRelationsOneToMany(advertising, review, relation, "TheAdvertisingReview", "theReview");
    }

    public static void userReview(Entity user, Entity review) {
        Property relation = review.addStringProperty("user_id").notNull().getProperty();
        makeRelationsOneToMany(user, review, relation, "TheUserReviews", "theReviews");
    }

    public static void deviceAdvertising(Entity device, Entity advertising) {
        Property relation = advertising.addStringProperty("device_id").getProperty();
        makeRelationsOneToMany(device, advertising, relation, "theDeviceAdvertising", "theAdvertising");
    }

    public static void locationDevice(Entity location, Entity device) {
        Property relation = device.addStringProperty("location_id").notNull().getProperty();
        makeRelationsOneToMany(location, device, relation, "theLocationDevice", "theDevices");
    }

    public static void locationCampaigns(Entity campaign, Entity location) {
        Property relation = location.addStringProperty("campaign_id").getProperty();
        makeRelationsOneToMany(campaign, location, relation, "theLocationCampaign", "theCampaign");
    }

    public static void interestCampaign(Entity campaign, Entity interest) {
        Property relation = interest.addStringProperty("campaign_id").getProperty();
        makeRelationsOneToMany(campaign, interest, relation, "theInterestCampaign", "theCampaigns");
    }

    public static void interestAdvertising(Entity advertising, Entity interest) {
        Property relation = interest.addStringProperty("advertising_id").getProperty();
        makeRelationsOneToMany(advertising, interest, relation, "theInterestAdvertising", "theInterests");
    }



}
