package com.optimussoftware.db;

import org.greenrobot.greendao.annotation.*;

import java.util.List;
import com.optimussoftware.db.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "CAMPAIGN".
 */
@SuppressWarnings("ALL")
@Entity(active = true)
public class Campaign implements java.io.Serializable {

    @Id
    @NotNull
    @Unique
    private String _id;
    private String _etag;
    private java.util.Date created;
    private java.util.Date updated;
    private Boolean active;
    private Boolean deleted;
    private String name;
    private java.util.Date finish_date;
    private java.util.Date start_date;
    private Integer start_hour;
    private Integer end_hour;
    private Integer max_age;
    private String efectiveDays;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient CampaignDao myDao;

    @ToMany(joinProperties = {
        @JoinProperty(name = "_id", referencedName = "campaign_id")
    })
    private List<Advertising> theCampaignAdvertising;

    @ToMany(joinProperties = {
        @JoinProperty(name = "_id", referencedName = "campaign_id")
    })
    private List<Location> theLocationCampaign;

    @ToMany(joinProperties = {
        @JoinProperty(name = "_id", referencedName = "campaign_id")
    })
    private List<Interest> theInterestCampaign;

    @ToMany(joinProperties = {
        @JoinProperty(name = "_id", referencedName = "campaign_id")
    })
    private List<NotificationAdvertising> theNotifiCampaign;

    @Generated
    public Campaign() {
    }

    public Campaign(String _id) {
        this._id = _id;
    }

    @Generated
    public Campaign(String _id, String _etag, java.util.Date created, java.util.Date updated, Boolean active, Boolean deleted, String name, java.util.Date finish_date, java.util.Date start_date, Integer start_hour, Integer end_hour, Integer max_age, String efectiveDays) {
        this._id = _id;
        this._etag = _etag;
        this.created = created;
        this.updated = updated;
        this.active = active;
        this.deleted = deleted;
        this.name = name;
        this.finish_date = finish_date;
        this.start_date = start_date;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
        this.max_age = max_age;
        this.efectiveDays = efectiveDays;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCampaignDao() : null;
    }

    @NotNull
    public String get_id() {
        return _id;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void set_id(@NotNull String _id) {
        this._id = _id;
    }

    public String get_etag() {
        return _etag;
    }

    public void set_etag(String _etag) {
        this._etag = _etag;
    }

    public java.util.Date getCreated() {
        return created;
    }

    public void setCreated(java.util.Date created) {
        this.created = created;
    }

    public java.util.Date getUpdated() {
        return updated;
    }

    public void setUpdated(java.util.Date updated) {
        this.updated = updated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(java.util.Date finish_date) {
        this.finish_date = finish_date;
    }

    public java.util.Date getStart_date() {
        return start_date;
    }

    public void setStart_date(java.util.Date start_date) {
        this.start_date = start_date;
    }

    public Integer getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(Integer start_hour) {
        this.start_hour = start_hour;
    }

    public Integer getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(Integer end_hour) {
        this.end_hour = end_hour;
    }

    public Integer getMax_age() {
        return max_age;
    }

    public void setMax_age(Integer max_age) {
        this.max_age = max_age;
    }

    public String getEfectiveDays() {
        return efectiveDays;
    }

    public void setEfectiveDays(String efectiveDays) {
        this.efectiveDays = efectiveDays;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Advertising> getTheCampaignAdvertising() {
        if (theCampaignAdvertising == null) {
            __throwIfDetached();
            AdvertisingDao targetDao = daoSession.getAdvertisingDao();
            List<Advertising> theCampaignAdvertisingNew = targetDao._queryCampaign_TheCampaignAdvertising(_id);
            synchronized (this) {
                if(theCampaignAdvertising == null) {
                    theCampaignAdvertising = theCampaignAdvertisingNew;
                }
            }
        }
        return theCampaignAdvertising;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetTheCampaignAdvertising() {
        theCampaignAdvertising = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Location> getTheLocationCampaign() {
        if (theLocationCampaign == null) {
            __throwIfDetached();
            LocationDao targetDao = daoSession.getLocationDao();
            List<Location> theLocationCampaignNew = targetDao._queryCampaign_TheLocationCampaign(_id);
            synchronized (this) {
                if(theLocationCampaign == null) {
                    theLocationCampaign = theLocationCampaignNew;
                }
            }
        }
        return theLocationCampaign;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetTheLocationCampaign() {
        theLocationCampaign = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<Interest> getTheInterestCampaign() {
        if (theInterestCampaign == null) {
            __throwIfDetached();
            InterestDao targetDao = daoSession.getInterestDao();
            List<Interest> theInterestCampaignNew = targetDao._queryCampaign_TheInterestCampaign(_id);
            synchronized (this) {
                if(theInterestCampaign == null) {
                    theInterestCampaign = theInterestCampaignNew;
                }
            }
        }
        return theInterestCampaign;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetTheInterestCampaign() {
        theInterestCampaign = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    @Generated
    public List<NotificationAdvertising> getTheNotifiCampaign() {
        if (theNotifiCampaign == null) {
            __throwIfDetached();
            NotificationAdvertisingDao targetDao = daoSession.getNotificationAdvertisingDao();
            List<NotificationAdvertising> theNotifiCampaignNew = targetDao._queryCampaign_TheNotifiCampaign(_id);
            synchronized (this) {
                if(theNotifiCampaign == null) {
                    theNotifiCampaign = theNotifiCampaignNew;
                }
            }
        }
        return theNotifiCampaign;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated
    public synchronized void resetTheNotifiCampaign() {
        theNotifiCampaign = null;
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

}
