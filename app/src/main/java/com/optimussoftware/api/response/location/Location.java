package com.optimussoftware.api.response.location;

import com.optimussoftware.api.response.commons.File;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leonardojpr on 10/4/16.
 */

public class Location implements Serializable {

    /**
     * Not-null value.
     */
    private String _id;
    private String _etag;
    private java.util.Date created;
    private java.util.Date updated;
    private Boolean active;
    private Boolean deleted;
    private String name;
    private String city;
    private String state;
    private String country;
    private String home_page;
    private String telephone;
    private String zip;
    private File logo;
    /**
     * Not-null value.
     */
    private String campaign_id;

    public Location() {
    }

    public Location(String _id) {
        this._id = _id;
    }

    public Location(String _id, String _etag, Date created, Date updated, Boolean active, Boolean deleted, String name, String city, String state, String country, String home_page, String telephone, String zip, File logo, String campaign_id) {
        this._id = _id;
        this._etag = _etag;
        this.created = created;
        this.updated = updated;
        this.active = active;
        this.deleted = deleted;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.home_page = home_page;
        this.telephone = telephone;
        this.zip = zip;
        this.logo = logo;
        this.campaign_id = campaign_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_etag() {
        return _etag;
    }

    public void set_etag(String _etag) {
        this._etag = _etag;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }
}