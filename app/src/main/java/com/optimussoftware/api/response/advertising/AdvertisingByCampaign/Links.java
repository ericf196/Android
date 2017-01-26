
package com.optimussoftware.api.response.advertising.AdvertisingByCampaign;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("self")
    @Expose
    private Self self;
    @SerializedName("parent")
    @Expose
    private Parent parent;

    /**
     * 
     * @return
     *     The self
     */
    public Self getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The parent
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * 
     * @param parent
     *     The parent
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

}
