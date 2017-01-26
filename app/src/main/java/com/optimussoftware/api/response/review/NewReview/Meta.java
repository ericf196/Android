
package com.optimussoftware.api.response.review.NewReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("max_results")
    @Expose
    private Integer maxResults;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total")
    @Expose
    private Integer total;

    /**
     * 
     * @return
     *     The maxResults
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * 
     * @param maxResults
     *     The max_results
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * 
     * @return
     *     The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * 
     * @param page
     *     The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

}
