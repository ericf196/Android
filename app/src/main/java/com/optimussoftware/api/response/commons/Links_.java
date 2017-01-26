
package com.optimussoftware.api.response.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class Links_ {

    @SerializedName("self")
    @Expose
    private Self_ self;

    /**
     * 
     * @return
     *     The self
     */
    public Self_ getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(Self_ self) {
        this.self = self;
    }

}
