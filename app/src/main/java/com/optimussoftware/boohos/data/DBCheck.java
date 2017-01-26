package com.optimussoftware.boohos.data;

import com.optimussoftware.db.Advertising;
import com.optimussoftware.db.AdvertisingDao;
import com.optimussoftware.db.Image;
import com.optimussoftware.db.ImageDao;
import com.optimussoftware.db.Interest;
import com.optimussoftware.db.InterestDao;
import com.optimussoftware.db.User;
import com.optimussoftware.db.UserDao;
import com.optimussoftware.db.UserInterest;
import com.optimussoftware.db.UserInterestDao;

import org.greenrobot.greendao.query.QueryBuilder;


/**
 * Created by william.castillo@optimus-software.com on 23/06/16.
 */
public class DBCheck {
    protected static boolean checkIfuserExist(UserDao userDao, String user_id) {
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties._id.eq(user_id));
        return qb.unique() != null;
    }

    protected static boolean checkIfAdvertisingExist(AdvertisingDao advertisingDao, String advertising_id) {
        QueryBuilder<Advertising> qb = advertisingDao.queryBuilder();
        qb.where(AdvertisingDao.Properties._id.eq(advertising_id));
        return qb.unique() != null;
    }

    protected static boolean checkIfImageExist(ImageDao imageDao, String image_id) {
        QueryBuilder<Image> qb = imageDao.queryBuilder();
        qb.where(ImageDao.Properties._id.eq(image_id));
        return qb.unique() != null;
    }

    protected static boolean checkIfInteresExist(InterestDao interestDao, String interes_id) {
        QueryBuilder<Interest> qb = interestDao.queryBuilder();
        qb.where(InterestDao.Properties._id.eq(interes_id));
        return qb.unique() != null;
    }

    protected static boolean checkIfUserInteresExist(UserInterestDao userInterestDao, Long userInteres_id) {
        QueryBuilder<UserInterest> qb = userInterestDao.queryBuilder();
        qb.where(UserInterestDao.Properties.Id.eq(userInteres_id));
        return qb.unique() != null;
    }

}
