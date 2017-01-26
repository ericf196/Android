
package com.optimussoftware.api.response.newAdvertising;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterestList {

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("indicator")
    @Expose
    private Boolean indicator;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String id;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getIndicator() {
        return indicator;
    }

    public void setIndicator(Boolean indicator) {
        this.indicator = indicator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
