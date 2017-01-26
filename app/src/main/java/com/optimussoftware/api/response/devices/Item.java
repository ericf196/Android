package com.optimussoftware.api.response.devices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Links;

public class Item {

    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name_key")
    @Expose
    private String nameKey;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("type")
    @Expose
    private Integer deviceType;
    @SerializedName("_updated")
    @Expose
    private String updated;

    /**
     * @return The locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * @param locationId The location_id
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
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
     * @return The nameKey
     */
    public String getNameKey() {
        return nameKey;
    }

    /**
     * @param nameKey The name_key
     */
    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
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
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return The deviceType
     */
    public Integer getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType The device_type
     */
    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

}
