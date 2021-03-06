
package com.optimussoftware.api.response.interest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Image;
import com.optimussoftware.api.response.commons.Links;

@SuppressWarnings("unused")
public class Item {

    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private Image logo;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("_active")
    @Expose
    private Boolean active;

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
     * @return The logo
     */
    public Image getLogo() {
        return logo;
    }

    /**
     * @param logo The logo
     */
    public void setLogo(Image logo) {
        this.logo = logo;
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
}