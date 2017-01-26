package com.optimussoftware.api.response.campaign.NewCampaign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * 
     * @return
     *     The href
     */
    public String getHref() {
        return href;
    }

    /**
     * 
     * @param href
     *     The href
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
