
package com.optimussoftware.api.response.review.ReviewCheck;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewCheck {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("vote")
    @Expose
    private Integer vote;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("advertising_id")
    @Expose
    private String advertisingId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

}
