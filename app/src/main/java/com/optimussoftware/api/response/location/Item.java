
package com.optimussoftware.api.response.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.File;
import com.optimussoftware.api.response.commons.Links;

public class Item {

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("is_free")
    @Expose
    private Boolean isFree;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("typebusiness_id")
    @Expose
    private String typebusinessId;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("address_street")
    @Expose
    private String addressStreet;
    @SerializedName("logo")
    @Expose
    private File logo;

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The _type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The isFree
     */
    public Boolean getIsFree() {
        return isFree;
    }

    /**
     * @param isFree The is_free
     */
    public void setIsFree(Boolean isFree) {
        this.isFree = isFree;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The delete
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * @param delete The _delete
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * @param etag The _etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * @return The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return The typebusinessId
     */
    public String getTypebusinessId() {
        return typebusinessId;
    }

    /**
     * @param typebusinessId The typebusiness_id
     */
    public void setTypebusinessId(String typebusinessId) {
        this.typebusinessId = typebusinessId;
    }

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The _created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * @param updated The _updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * @return The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * @param links The _links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone The telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return The customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId The customer_id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * @return The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active The _active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return The addressStreet
     */
    public String getAddressStreet() {
        return addressStreet;
    }

    /**
     * @param addressStreet The address_street
     */
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    /**
     * @return The logo
     */
    public File getLogo() {
        return logo;
    }

    /**
     * @param logo The logo
     */
    public void setLogo(File logo) {
        this.logo = logo;
    }

}
