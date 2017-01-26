package com.optimussoftware.api.response.advertising;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterestList {

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("inherited")
    @Expose
    private Boolean inherited;

    /**
     * @return The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
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
     * @return The inherited
     */
    public Boolean getInherited() {
        return inherited;
    }

    /**
     * @param inherited The inherited
     */
    public void setInherited(Boolean inherited) {
        this.inherited = inherited;
    }

}
