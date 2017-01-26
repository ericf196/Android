package com.optimussoftware.api.response.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Links;

public class Item {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_created")
    @Expose
    private String created;

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * @return The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
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

}
