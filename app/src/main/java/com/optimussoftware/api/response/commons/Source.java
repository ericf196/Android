package com.optimussoftware.api.response.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by leonardojpr on 10/5/16.
 */

public class Source {

    @SerializedName("advertising_id")
    @Expose
    private String advertisingId;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("_updated")
    @Expose
    private String updated;

    /**
     *
     * @return
     * The advertisingId
     */
    public String getAdvertisingId() {
        return advertisingId;
    }

    /**
     *
     * @param advertisingId
     * The advertising_id
     */
    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

    /**
     *
     * @return
     * The created
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The _created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     *
     * @param etag
     * The _etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     *
     * @return
     * The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     *
     * @param updated
     * The _updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

}
