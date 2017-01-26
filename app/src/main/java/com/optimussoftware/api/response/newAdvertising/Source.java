
package com.optimussoftware.api.response.newAdvertising;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("typebusiness_id")
    @Expose
    private TypebusinessId typebusinessId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("home_page")
    @Expose
    private String homePage;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("is_free")
    @Expose
    private Boolean isFree;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("address_street")
    @Expose
    private String addressStreet;
    @SerializedName("ubication")
    @Expose
    private Ubication ubication;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("telephone")
    @Expose
    private String telephone;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public TypebusinessId getTypebusinessId() {
        return typebusinessId;
    }

    public void setTypebusinessId(TypebusinessId typebusinessId) {
        this.typebusinessId = typebusinessId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIsFree() {
        return isFree;
    }

    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public Ubication getUbication() {
        return ubication;
    }

    public void setUbication(Ubication ubication) {
        this.ubication = ubication;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

}
