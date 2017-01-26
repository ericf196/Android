
package com.optimussoftware.api.response.advertising.AdvertisingByCampaign;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertisingListByCampaign {

    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("_items")
    @Expose
    private List<Item> items = new ArrayList<Item>();
    @SerializedName("_meta")
    @Expose
    private Meta meta;

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
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The _items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * 
     * @return
     *     The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * 
     * @param meta
     *     The _meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
