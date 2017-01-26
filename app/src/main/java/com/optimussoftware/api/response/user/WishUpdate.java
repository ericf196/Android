package com.optimussoftware.api.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by leonardojpr on 13/01/17.
 */

public class WishUpdate {

    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("wishes")
    @Expose
    private List<String> updated;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public List<String> getUpdated() {
        return updated;
    }

    public void setUpdated(List<String> updated) {
        this.updated = updated;
    }
}
