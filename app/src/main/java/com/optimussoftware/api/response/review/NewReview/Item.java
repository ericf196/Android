
package com.optimussoftware.api.response.review.NewReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("advertising_id")
    @Expose
    private String advertisingId;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("user_id")
    @Expose
    private UserId userId;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("vote")
    @Expose
    private Integer vote;
    @SerializedName("_links")
    @Expose
    private Links_ links;

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

    /**
     * 
     * @return
     *     The advertisingId
     */
    public String getAdvertisingId() {
        return advertisingId;
    }

    /**
     * 
     * @param advertisingId
     *     The advertising_id
     */
    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

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
     *     The userId
     */
    public UserId getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(UserId userId) {
        this.userId = userId;
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
     *     The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * 
     * @param lang
     *     The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
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
     *     The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     *     The vote
     */
    public Integer getVote() {
        return vote;
    }

    /**
     * 
     * @param vote
     *     The vote
     */
    public void setVote(Integer vote) {
        this.vote = vote;
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

}
