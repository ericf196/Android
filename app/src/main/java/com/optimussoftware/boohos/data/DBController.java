package com.optimussoftware.boohos.data;

import android.content.Context;
import android.util.Log;

import com.optimussoftware.api.core.Factory;
import com.optimussoftware.api.response.interest.InterestList;
import com.optimussoftware.api.response.newAdvertising.AdverstingsList;
import com.optimussoftware.api.response.userinterest.UserInterestList;
import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.AdvertisingDao;
import com.optimussoftware.db.AdvertisingImage;
import com.optimussoftware.db.AdvertisingImageDao;
import com.optimussoftware.db.AdvertisingInterest;
import com.optimussoftware.db.AdvertisingInterestDao;
import com.optimussoftware.db.Campaign;
import com.optimussoftware.db.CampaignDao;
import com.optimussoftware.db.DaoMaster;
import com.optimussoftware.db.DaoSession;
import com.optimussoftware.db.Dislikes;
import com.optimussoftware.db.DislikesDao;
import com.optimussoftware.db.Favorites;
import com.optimussoftware.db.FavoritesDao;
import com.optimussoftware.db.Image;
import com.optimussoftware.db.ImageDao;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.InterestDao;
import com.optimussoftware.db.Likes;
import com.optimussoftware.db.LikesDao;
import com.optimussoftware.db.Location;
import com.optimussoftware.db.LocationDao;
import com.optimussoftware.db.NotificationAdvertising;
import com.optimussoftware.db.NotificationAdvertisingDao;
import com.optimussoftware.db.Review;
import com.optimussoftware.db.ReviewDao;
import com.optimussoftware.db.User;
import com.optimussoftware.db.UserDao;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.db.UserInterestDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


public class DBController {

    private static final String TAG = DBController.class.getSimpleName();

    private static DBController dbController = null;
    private DaoMaster daoMaster = null;

    private DBController() {
    }

    public static DBController getControler() {
        if (dbController == null)
            dbController = new DBController();
        return dbController;
    }

    public void createDB(Context context) {
        DaoMaster.createAllTables(connection(context), true);
    }

    @SuppressWarnings("unused")
    public void cleanBD(Context context) {
        DaoMaster daoMaster = new DaoMaster(connection(context));
        daoMaster.newSession().clear();
    }

    private Database connection(Context context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, Constants.DBNAME, null);
        Database db = openHelper.getWritableDb();
        return db;
    }

    private DaoSession getSession(Context context) {
        if (daoMaster == null)
            daoMaster = new DaoMaster(connection(context));
        return daoMaster.newSession();
    }

    public void createUser(Context context, User user) {
        UserDao userDao = getSession(context).getUserDao();
        userDao.insertOrReplace(user);
    }

    public void createImage(Context context, Image image) {
        ImageDao imageDao = getSession(context).getImageDao();
        imageDao.insertOrReplace(image);
    }


    public void createUserInterest(Context context, UserInterest userInterest) {
        UserInterestDao userInterestDao = getSession(context).getUserInterestDao();
        userInterestDao.insertOrReplace(userInterest);
    }

    public void createAdvertising(Context context, Advertising advertising) {
        AdvertisingDao advertisingDao = getSession(context).getAdvertisingDao();
        advertisingDao.insertOrReplace(advertising);
    }

    public void createLike(Context context, Likes likes) {
        LikesDao likesDao = getSession(context).getLikesDao();
        likesDao.insertOrReplace(likes);
    }

    public void createDislike(Context context, Dislikes dislikes) {
        DislikesDao dislikesDao = getSession(context).getDislikesDao();
        dislikesDao.insertOrReplace(dislikes);
    }

    public void createReview(Context context, Review review) {
        ReviewDao reviewDao = getSession(context).getReviewDao();
        reviewDao.insertOrReplace(review);
    }

    public void createCampaign(Context context, Campaign campaign) {
        CampaignDao campaignDao = getSession(context).getCampaignDao();
        campaignDao.insertOrReplace(campaign);
    }

    public void createAdvertisingNotification(Context context, NotificationAdvertising notificationAdvertising) {
        NotificationAdvertisingDao notificationAdvertisingDao = getSession(context).getNotificationAdvertisingDao();
        notificationAdvertisingDao.insertOrReplace(notificationAdvertising);
    }

    public void createInterest(Context context, Interest interest) {
        InterestDao interestDao = getSession(context).getInterestDao();
        interestDao.insertOrReplace(interest);
    }

    public void createLocation(Context context, Location location) {
        LocationDao locationDao = getSession(context).getLocationDao();
        locationDao.insertOrReplace(location);
    }

    public void createFavorite(Context context, Favorites favorites) {
        FavoritesDao favoritesDao = getSession(context).getFavoritesDao();
        favoritesDao.insertOrReplace(favorites);
    }

    public void createAdvertisingImage(Context context, AdvertisingImage advertisingImage) {
        AdvertisingImageDao advertisingImageDao = getSession(context).getAdvertisingImageDao();
        advertisingImageDao.insertOrReplace(advertisingImage);
    }

    public void createAdvertisingInterest(Context context, AdvertisingInterest advertisingInterest) {
        AdvertisingInterestDao advertisingInterestDao = getSession(context).getAdvertisingInterestDao();
        advertisingInterestDao.insertOrReplace(advertisingInterest);
    }

    public void removeLike(Context context, Likes likes) {
        LikesDao likesDao = getSession(context).getLikesDao();
        likesDao.delete(likes);
    }

    public void removeDislike(Context context, Dislikes dislikes) {
        DislikesDao dislikesDao = getSession(context).getDislikesDao();
        dislikesDao.delete(dislikes);
    }

    public void removeReview(Context context, Review review) {
        ReviewDao reviewDao = getSession(context).getReviewDao();
        reviewDao.delete(review);
    }

    public void removeUserInterest(Context context, UserInterest userInterest) {
        UserInterestDao userInterestDao = getSession(context).getUserInterestDao();
        userInterestDao.delete(userInterest);
    }

    public void removeFavorite(Context context, Favorites favorites) {
        FavoritesDao favoritesDao = getSession(context).getFavoritesDao();
        favoritesDao.delete(favorites);
    }

    public void removeAdvertising(Context context, Advertising advertising) {
        AdvertisingDao advertisingDao = getSession(context).getAdvertisingDao();
        advertisingDao.delete(advertising);
    }

    public void removeNotificationAdvertising(Context context, NotificationAdvertising notificationAdvertising) {
        NotificationAdvertisingDao notificationAdvertisingDao = getSession(context).getNotificationAdvertisingDao();
        notificationAdvertisingDao.delete(notificationAdvertising);
    }

    public void removeCampaign(Context context, Campaign campaign) {
        CampaignDao campaignDao = getSession(context).getCampaignDao();
        campaignDao.delete(campaign);
    }

    public String storeOffertList(Context context, AdverstingsList list) {
        String ids = "";
        boolean first = true;
        Advertising advertising;
        final DaoSession session = getSession(context);
        AdvertisingDao advertisingDao = session.getAdvertisingDao();
        for (final com.optimussoftware.api.response.newAdvertising.Item item : list.getItems()) {
            ids += first ? item.getId() : "~" + item.getId();
            advertising = Factory.getAdvertisingFromResponse(item);
            advertisingDao.insertOrReplace(advertising);
            ImageDao imageDao = session.getImageDao();
            Image images;
            for (com.optimussoftware.api.response.commons.Image image : item.getImages()) {
                if (!DBCheck.checkIfImageExist(imageDao, image.getId())) {
                    images = Factory.getImageFromResponse(image, item.getId());
                    imageDao.insert(images);
                }

                if (checkAdvertisingImageList(context, item.getId(), image.getId()).size() == 0) {
                    AdvertisingImage advertisingImage = new AdvertisingImage();
                    advertisingImage.setAdvertising_id(item.getId());
                    advertisingImage.setImage_id(image.getId());
                    DBController.getControler().createAdvertisingImage(context, advertisingImage);
                    Log.d(TAG, "Image register" + image.getId() + " " + item.getId());
                }

            }

            if (DBController.getControler().getCampaign(context, item.getCampaignId().getId()) == null) {
                Log.i(TAG, "add campaign");

                DBController.getControler().createCampaign(context, Factory.getCampaignFromResponse(item.getCampaignId()));
            } else {
                Log.i(TAG, "exist campaign");
            }
            first = false;
        }
        return ids;
    }

    public void createImagenFromAdvertising(Context context, com.optimussoftware.api.response.favorites.advertisingFavorite.AdvertisingId advertisingId) {
        final DaoSession session = getSession(context);
       /* ImageDao imageDao = session.getImageDao();
        //todo
        //todo
        //todo
        //todo
        for (int i = 0; i < advertisingId.getImages().size(); i++) {
            Image img = Factory.getImageFromResponse(advertisingId.getImages().get(i), advertisingId.getId());
            if (!DBCheck.checkIfImageExist(imageDao, img.get_id())) {
                DBController.getControler().createImage(context, img);
            }
            if (checkAdvertisingImageList(context, advertisingId.getId(), img.get_id()).size() == 0) {
                AdvertisingImage advertisingImage = new AdvertisingImage();
                advertisingImage.setAdvertising_id(advertisingId.getId());
                advertisingImage.setImage_id(advertisingId.getImages().get(i).getId());
                DBController.getControler().createAdvertisingImage(context, advertisingImage);
            }
        }
        //todo
        //todo
        //todo
        //todo */
    }

    public void storeInterestList(Context context, InterestList interestList, List<Interest> list) {
        DaoSession session = getSession(context);
        InterestDao interestDao = session.getInterestDao();
        for (com.optimussoftware.api.response.interest.Item item : interestList.getItems()) {
            Interest interest = Factory.getInterestFromResponse(item);
            list.add(interest);
            if (!DBCheck.checkIfInteresExist(interestDao, interest.get_id())) {
                interestDao.insert(interest);
            }
        }
    }

    public void storeUserInterestList(Context context, UserInterestList interestList, List<UserInterest> list) {
        DaoSession session = getSession(context);
        UserInterestDao interestDao = session.getUserInterestDao();
        for (com.optimussoftware.api.response.userinterest.Item item : interestList.getItems()) {
            UserInterest interest = Factory.getUserInterestFromResponse(item);
            list.add(interest);
            if (!DBCheck.checkIfUserInteresExist(interestDao, interest.getId())) {
                interestDao.insert(interest);
            }
        }
    }

    public Advertising getAdvertising(Context context, String uid) {
        DaoSession session = getSession(context);
        AdvertisingDao advertisingDao = session.getAdvertisingDao();
        return advertisingDao.load(uid);
    }

    public User getUser(Context context, String uid) {
        if (uid == null) return null;
        UserDao userDao = getSession(context).getUserDao();
        User user = userDao.load(uid);
        return user;
    }

    public Campaign getCampaign(Context context, String campaign_id) {
        DaoSession session = getSession(context);
        CampaignDao campaignDao = session.getCampaignDao();
        return campaignDao.load(campaign_id);
    }

    public Location getLocation(Context context, String location_id) {
        DaoSession session = getSession(context);
        LocationDao locationDao = session.getLocationDao();
        return locationDao.load(location_id);
    }


    public List<UserInterest> getUserInterest(Context context, String user_id, String interest_id) {
        DaoSession session = getSession(context);
        UserInterestDao userInterestDao = session.getUserInterestDao();
        QueryBuilder<UserInterest> qb = userInterestDao.queryBuilder();
        qb.where(UserInterestDao.Properties.User_id.eq(user_id), UserInterestDao.Properties.Interest_id.eq(interest_id));
        return qb.list();
    }

    public List<UserInterest> getUserInterest(Context context, String user_id) {
        DaoSession session = getSession(context);
        UserInterestDao userInterestDao = session.getUserInterestDao();
        QueryBuilder<UserInterest> qb = userInterestDao.queryBuilder();
        qb.where(UserInterestDao.Properties.User_id.eq(user_id));
        return qb.list();
    }

    public List<AdvertisingImage> getAdvertisingImageList(Context context, String advertising_id) {
        DaoSession session = getSession(context);
        AdvertisingImageDao advertisingImageDao = session.getAdvertisingImageDao();
        QueryBuilder<AdvertisingImage> qb = advertisingImageDao.queryBuilder();
        qb.where(AdvertisingImageDao.Properties.Advertising_id.eq(advertising_id));
        return qb.list();
    }

    public List<AdvertisingImage> checkAdvertisingImageList(Context context, String advertising_id, String image_id) {
        DaoSession session = getSession(context);
        AdvertisingImageDao advertisingImageDao = session.getAdvertisingImageDao();
        QueryBuilder<AdvertisingImage> qb = advertisingImageDao.queryBuilder();
        qb.where(AdvertisingImageDao.Properties.Advertising_id.eq(advertising_id), AdvertisingImageDao.Properties.Image_id.eq(image_id));
        return qb.list();
    }

    public List<AdvertisingInterest> getAdvertisingInterestList(Context context, String advertising_id) {
        DaoSession session = getSession(context);
        AdvertisingInterestDao advertisingInterestDao = session.getAdvertisingInterestDao();
        QueryBuilder<AdvertisingInterest> qb = advertisingInterestDao.queryBuilder();
        qb.where(AdvertisingInterestDao.Properties.Advertising_id.eq(advertising_id));
        return qb.list();
    }

    public List<AdvertisingInterest> checkAdvertisingInterestList(Context context, String interest_id, String advertising_id) {
        DaoSession session = getSession(context);
        AdvertisingInterestDao advertisingInterestDao = session.getAdvertisingInterestDao();
        QueryBuilder<AdvertisingInterest> qb = advertisingInterestDao.queryBuilder();
        qb.where(AdvertisingInterestDao.Properties.Interest_id.eq(interest_id), AdvertisingInterestDao.Properties.Advertising_id.eq(advertising_id));
        return qb.list();
    }

    public List<NotificationAdvertising> getNotificationAdvertisingList(Context context, String advertising_id) {
        DaoSession session = getSession(context);
        NotificationAdvertisingDao notificationAdvertisingDao = session.getNotificationAdvertisingDao();
        QueryBuilder<NotificationAdvertising> qb = notificationAdvertisingDao.queryBuilder();
        qb.where(NotificationAdvertisingDao.Properties.Advertising_id.eq(advertising_id));
        return qb.list();
    }


    public Image getImage(Context context, String image_id) {
        DaoSession session = getSession(context);
        ImageDao imageDao = session.getImageDao();
        return imageDao.load(image_id);
    }


    public void sincronizeInterestData(Context context, List<Interest> interests) {
        DaoSession session = getSession(context);
        InterestDao interestDao = session.getInterestDao();
        UserInterestDao userInterestDao = session.getUserInterestDao();

        for (Interest interest : interests) {
            interestDao.insertOrReplace(interest);
        }
    }

    public List<Campaign> getCampaignList(Context context) {
        return getSession(context).getCampaignDao().loadAll();
    }

    public List<Interest> getInterestList(Context context) {
        return getSession(context).getInterestDao().loadAll();
    }

    public List<Advertising> getAdvertisingList(Context context) {
        return getSession(context).getAdvertisingDao().loadAll();
    }

    public List<NotificationAdvertising> getNotificationAdvertisingList(Context context) {
        return getSession(context).getNotificationAdvertisingDao().loadAll();
    }

    public Interest getInterest(Context context, String interest_id) {
        DaoSession session = getSession(context);
        InterestDao interestDao = session.getInterestDao();
        return interestDao.load(interest_id);
    }

    public List<UserInterest> getInterestByUser(Context context, String user_id) {
        DaoSession session = getSession(context);
        UserInterestDao userInterestDao = session.getUserInterestDao();
        QueryBuilder<UserInterest> qb = userInterestDao.queryBuilder();
        qb.where(UserInterestDao.Properties.User_id.eq(user_id));
        return qb.list();
    }

    public List<Likes> getLikesByUser(Context context, String user_id, String advertising_id) {
        DaoSession session = getSession(context);
        LikesDao likesDao = session.getLikesDao();
        QueryBuilder<Likes> qb = likesDao.queryBuilder();
        qb.where(LikesDao.Properties.User_id.eq(user_id), LikesDao.Properties.Advertising_id.eq(advertising_id));
        return qb.list();
    }

    public List<Dislikes> getDislikesByUser(Context context, String user_id, String advertising_id) {
        DaoSession session = getSession(context);
        DislikesDao dislikesDao = session.getDislikesDao();
        QueryBuilder<Dislikes> qb = dislikesDao.queryBuilder();
        qb.where(DislikesDao.Properties.User_id.eq(user_id), DislikesDao.Properties.Advertising_id.eq(advertising_id));
        return qb.list();
    }

    public List<Favorites> getFavoritesByAdvertising(Context context, String user_id, String advertising_id) {
        DaoSession session = getSession(context);
        FavoritesDao favoritesDao = session.getFavoritesDao();
        QueryBuilder<Favorites> qb = favoritesDao.queryBuilder();
        qb.where(FavoritesDao.Properties.User_id.eq(user_id), FavoritesDao.Properties.Advertising_id.eq(advertising_id));
        return qb.list();
    }

    public List<Favorites> getFavoritesByUser(Context context, String user_id) {
        DaoSession session = getSession(context);
        FavoritesDao favoritesDao = session.getFavoritesDao();
        QueryBuilder<Favorites> qb = favoritesDao.queryBuilder();
        qb.where(FavoritesDao.Properties.User_id.eq(user_id));
        return qb.list();
    }

    public List<Advertising> getAdvertisingListByCampaign(Context context, String campaign_id) {
        DaoSession session = getSession(context);
        AdvertisingDao advertisingDao = session.getAdvertisingDao();
        QueryBuilder<Advertising> qb = advertisingDao.queryBuilder();
        qb.where(AdvertisingDao.Properties.Campaign_id.eq(campaign_id));
        return qb.list();
    }

}