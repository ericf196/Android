package com.optimussoftware.api.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Logo;

public class Wish {

    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("logo")
    @Expose
    private Logo logo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_active")
    @Expose
    private Boolean active;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
