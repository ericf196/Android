
package com.optimussoftware.api.response.userinterest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignInterestResponse {

    @SerializedName("_status")
    @Expose
    private String status;
    @SerializedName("_created")
    @Expose
    private Integer created;
    @SerializedName("_try_deleted")
    @Expose
    private Integer tryDeleted;

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The _status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The created
     */
    public Integer getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The _created
     */
    public void setCreated(Integer created) {
        this.created = created;
    }

    /**
     * 
     * @return
     *     The tryDeleted
     */
    public Integer getTryDeleted() {
        return tryDeleted;
    }

    /**
     * 
     * @param tryDeleted
     *     The _try_deleted
     */
    public void setTryDeleted(Integer tryDeleted) {
        this.tryDeleted = tryDeleted;
    }

}
