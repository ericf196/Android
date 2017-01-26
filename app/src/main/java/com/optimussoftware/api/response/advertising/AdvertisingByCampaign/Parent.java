
package com.optimussoftware.api.response.advertising.AdvertisingByCampaign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("href")
    @Expose
    private String href;

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

}
