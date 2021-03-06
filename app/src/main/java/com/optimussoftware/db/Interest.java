package com.optimussoftware.db;

import org.greenrobot.greendao.annotation.*;

import com.optimussoftware.db.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "INTEREST".
 */
@SuppressWarnings("ALL")
@Entity(active = true)
public class Interest implements java.io.Serializable {

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
    private String image;
    private String description;
    private Boolean indicator;
    private String campaign_id;
    private String advertising_id;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient InterestDao myDao;

    @ToOne(joinProperty = "campaign_id")
    private Campaign theCampaigns;

    @Generated
    private transient String theCampaigns__resolvedKey;

    @ToOne(joinProperty = "advertising_id")
    private Advertising theInterests;

    @Generated
    private transient String theInterests__resolvedKey;

    @Generated
    public Interest() {
    }

    public Interest(String _id) {
        this._id = _id;
    }

    @Generated
    public Interest(String _id, String _etag, java.util.Date created, java.util.Date updated, Boolean active, Boolean deleted, String name, String image, String description, Boolean indicator, String campaign_id, String advertising_id) {
        this._id = _id;
        this._etag = _etag;
        this.created = created;
        this.updated = updated;
        this.active = active;
        this.deleted = deleted;
        this.name = name;
        this.image = image;
        this.description = description;
        this.indicator = indicator;
        this.campaign_id = campaign_id;
        this.advertising_id = advertising_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInterestDao() : null;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIndicator() {
        return indicator;
    }

    public void setIndicator(Boolean indicator) {
        this.indicator = indicator;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getAdvertising_id() {
        return advertising_id;
    }

    public void setAdvertising_id(String advertising_id) {
        this.advertising_id = advertising_id;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Campaign getTheCampaigns() {
        String __key = this.campaign_id;
        if (theCampaigns__resolvedKey == null || theCampaigns__resolvedKey != __key) {
            __throwIfDetached();
            CampaignDao targetDao = daoSession.getCampaignDao();
            Campaign theCampaignsNew = targetDao.load(__key);
            synchronized (this) {
                theCampaigns = theCampaignsNew;
            	theCampaigns__resolvedKey = __key;
            }
        }
        return theCampaigns;
    }

    @Generated
    public void setTheCampaigns(Campaign theCampaigns) {
        synchronized (this) {
            this.theCampaigns = theCampaigns;
            campaign_id = theCampaigns == null ? null : theCampaigns.get_id();
            theCampaigns__resolvedKey = campaign_id;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Advertising getTheInterests() {
        String __key = this.advertising_id;
        if (theInterests__resolvedKey == null || theInterests__resolvedKey != __key) {
            __throwIfDetached();
            AdvertisingDao targetDao = daoSession.getAdvertisingDao();
            Advertising theInterestsNew = targetDao.load(__key);
            synchronized (this) {
                theInterests = theInterestsNew;
            	theInterests__resolvedKey = __key;
            }
        }
        return theInterests;
    }

    @Generated
    public void setTheInterests(Advertising theInterests) {
        synchronized (this) {
            this.theInterests = theInterests;
            advertising_id = theInterests == null ? null : theInterests.get_id();
            theInterests__resolvedKey = advertising_id;
        }
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
