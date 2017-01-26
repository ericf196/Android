package com.optimussoftware.api.response.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Links_;
import com.optimussoftware.api.response.commons.Meta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardojpr on 9/23/16.
 */
public class CustomerList {

    @SerializedName("_meta")
    @Expose
    private Meta meta;
    @SerializedName("_items")
    @Expose
    private List<com.optimussoftware.api.response.customer.Item> items = new ArrayList<com.optimussoftware.api.response.customer.Item>();
    @SerializedName("_links")
    @Expose
    private Links_ links;

    /**
     * @return The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * @param meta The _meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * @return The items
     */
    public List<com.optimussoftware.api.response.customer.Item> getItems() {
        return items;
    }

    /**
     * @param items The _items
     */
    public void setItems(List<com.optimussoftware.api.response.customer.Item> items) {
        this.items = items;
    }

    /**
     * @return The links
     */
    public Links_ getLinks() {
        return links;
    }

    /**
     * @param links The _links
     */
    public void setLinks(Links_ links) {
        this.links = links;
    }


}
