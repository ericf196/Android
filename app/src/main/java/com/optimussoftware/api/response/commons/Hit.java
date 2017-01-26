package com.optimussoftware.api.response.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {

    @SerializedName("_source")
    @Expose
    private Source source;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("_score")
    @Expose
    private Integer score;
    @SerializedName("_index")
    @Expose
    private String index;
    @SerializedName("_id")
    @Expose
    private String id;

    /**
     *
     * @return
     *     The source
     */
    public Source getSource() {
        return source;
    }

    /**
     *
     * @param source
     *     The _source
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     *
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The _type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     *     The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     *
     * @param score
     *     The _score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     *
     * @return
     *     The index
     */
    public String getIndex() {
        return index;
    }

    /**
     *
     * @param index
     *     The _index
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The _id
     */
    public void setId(String id) {
        this.id = id;
    }

}
