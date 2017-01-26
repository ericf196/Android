
package com.optimussoftware.api.response.favorites.advertisingFavorite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Links_;

public class Item {

    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_links")
    @Expose
    private Links_ links;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("advertising_id")
    @Expose
    private AdvertisingId advertisingId;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_updated")
    @Expose
    private String updated;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The _type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The links
     */
    public Links_ getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The _links
     */
    public void setLinks(Links_ links) {
        this.links = links;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * 
     * @param etag
     *     The _etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * 
     * @return
     *     The locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * 
     * @param locationId
     *     The location_id
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The advertisingId
     */
    public AdvertisingId getAdvertisingId() {
        return advertisingId;
    }

    /**
     * 
     * @param advertisingId
     *     The advertising_id
     */
    public void setAdvertisingId(AdvertisingId advertisingId) {
        this.advertisingId = advertisingId;
    }

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The _created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * 
     * @return
     *     The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The _updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

}
