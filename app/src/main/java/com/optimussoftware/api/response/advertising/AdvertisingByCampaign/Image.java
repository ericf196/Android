
package com.optimussoftware.api.response.advertising.AdvertisingByCampaign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("file")
    @Expose
    private File file;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_active")
    @Expose
    private Boolean active;

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
     *     The delete
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * 
     * @param delete
     *     The _delete
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
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
     *     The file
     */
    public File getFile() {
        return file;
    }

    /**
     * 
     * @param file
     *     The file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 
     * @return
     *     The customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 
     * @param customerId
     *     The customer_id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
     *     The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(Integer width) {
        this.width = width;
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
     *     The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * 
     * @param active
     *     The _active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

}
