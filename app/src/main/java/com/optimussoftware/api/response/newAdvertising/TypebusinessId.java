
package com.optimussoftware.api.response.newAdvertising;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypebusinessId {

    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("_created")
    @Expose
    private String created;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
