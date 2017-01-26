package com.optimussoftware.generator.db;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class HemerModelGenerator {


    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");
    private static final String OUT_DIR = PROJECT_DIR + "/app/src/main/java/";

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.optimussoftware.db");
        addTables(schema);
        System.out.println(OUT_DIR);
        DaoGenerator generator = new DaoGenerator();
        generator.generateAll(schema, OUT_DIR);
    }

    private static void addTables(Schema schema) {
        /* entities */
        Entity user = TableDefinition.addUser(schema);
        Entity advertising = TableDefinition.addAdvertising(schema);
        Entity image = TableDefinition.addImage(schema);
        Entity interest = TableDefinition.addInterest(schema);
        Entity userInterst = TableDefinition.addUserInterest(schema);
        Entity visit = TableDefinition.addVisit(schema);
        Entity likes = TableDefinition.addLikes(schema);
        Entity dislikes = TableDefinition.addDislikes(schema);
        Entity favorites = TableDefinition.addFavorites(schema);
        Entity notification = TableDefinition.addNotification(schema);
        Entity campaign = TableDefinition.addCampaign(schema);
        Entity location = TableDefinition.addLocation(schema);
        Entity review = TableDefinition.addReview(schema);
        Entity devices = TableDefinition.addDevices(schema);
        Entity advertisingImage = TableDefinition.addAdvertisingImage(schema);
        Entity advertisingInterest = TableDefinition.addAdvertisinginterest(schema);
        Entity notificationAdvertising = TableDefinition.addNotificationAdvertising(schema);

        /* relationships between entities */

        TableRelations.advertisingImage(advertising, image);
        TableRelations.advertisingNotification(advertising, notification);
        TableRelations.advertisingReview(advertising, review);
        TableRelations.campaignAdvertising(campaign, advertising);
        TableRelations.deviceAdvertising(devices, advertising);
        TableRelations.locationDevice(location, devices);
        TableRelations.userDislike(user, dislikes);
        TableRelations.userFavorite(user, favorites);
        TableRelations.locationFavorite(location, favorites);
        TableRelations.userLike(user, likes);
        TableRelations.userUsrInterest(user, userInterst);
        TableRelations.userReview(user, review);
        TableRelations.userVisit(user, visit);
        TableRelations.locationCampaigns(campaign, location);
        TableRelations.interestCampaign(campaign, interest);
        TableRelations.interestAdvertising(advertising, interest);
        TableRelations.notificationAdvertising(advertising, notificationAdvertising);
        TableRelations.notificationCampaign(campaign, notificationAdvertising);
    }
}