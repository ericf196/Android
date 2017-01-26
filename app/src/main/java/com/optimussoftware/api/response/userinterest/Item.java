
package com.optimussoftware.api.response.userinterest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Links;

public class Item {

    @SerializedName("interest_id")
    @Expose
    private String interestId;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     *
     * @return
     *     The interestId
     */
    public String getInterestId() {
        return interestId;
    }

    /**
     *
     * @param interestId
     *     The interest_id
     */
    public void setInterestId(String interestId) {
        this.interestId = interestId;
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
     *     The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     *
     * @param links
     *     The _links
     */
    public void setLinks(Links links) {
        this.links = links;
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

}
