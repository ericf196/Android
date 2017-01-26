
package com.optimussoftware.api.response.newAdvertising;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdverstingsList {

    @SerializedName("_items")
    @Expose
    private List<Item> items = null;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
