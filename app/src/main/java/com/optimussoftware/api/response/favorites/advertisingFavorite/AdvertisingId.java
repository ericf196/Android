
package com.optimussoftware.api.response.favorites.advertisingFavorite;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Image;

public class AdvertisingId {

    @SerializedName("count_likes")
    @Expose
    private Integer countLikes;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("count_dislikes")
    @Expose
    private Integer countDislikes;
    @SerializedName("trackeable")
    @Expose
    private Boolean trackeable;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("interest_list")
    @Expose
    private List<String> interestList = new ArrayList<String>();
    @SerializedName("count_votes")
    @Expose
    private Integer countVotes;
    @SerializedName("_etag")
    @Expose
    private String etag;
    //todo
    //todo
    //todo
    /*@SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();*/
    @SerializedName("minimun_hits")
    @Expose
    private Integer minimunHits;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("acum_votes")
    @Expose
    private Integer acumVotes;
    @SerializedName("count_open")
    @Expose
    private Integer countOpen;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("campaign_id")
    @Expose
    private String campaignId;

    /**
     * 
     * @return
     *     The countLikes
     */
    public Integer getCountLikes() {
        return countLikes;
    }

    /**
     * 
     * @param countLikes
     *     The count_likes
     */
    public void setCountLikes(Integer countLikes) {
        this.countLikes = countLikes;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The countDislikes
     */
    public Integer getCountDislikes() {
        return countDislikes;
    }

    /**
     * 
     * @param countDislikes
     *     The count_dislikes
     */
    public void setCountDislikes(Integer countDislikes) {
        this.countDislikes = countDislikes;
    }

    /**
     * 
     * @return
     *     The trackeable
     */
    public Boolean getTrackeable() {
        return trackeable;
    }

    /**
     * 
     * @param trackeable
     *     The trackeable
     */
    public void setTrackeable(Boolean trackeable) {
        this.trackeable = trackeable;
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

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The _created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * 
     * @param updated
     *     The _updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * 
     * @return
     *     The interestList
     */
    public List<String> getInterestList() {
        return interestList;
    }

    /**
     * 
     * @param interestList
     *     The interest_list
     */
    public void setInterestList(List<String> interestList) {
        this.interestList = interestList;
    }

    /**
     * 
     * @return
     *     The countVotes
     */
    public Integer getCountVotes() {
        return countVotes;
    }

    /**
     * 
     * @param countVotes
     *     The count_votes
     */
    public void setCountVotes(Integer countVotes) {
        this.countVotes = countVotes;
    }

    /**
     * 
     * @return
     *     The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * 
     * @param etag
     *     The _etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }
    //todo
    //todo
    //todo
//    /**
//     *
//     * @return
//     *     The images
//     */
//    public List<Image> getImages() {
//        return images;
//    }
//
//    /**
//     *
//     * @param images
//     *     The images
//     */
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }

    /**
     * 
     * @return
     *     The minimunHits
     */
    public Integer getMinimunHits() {
        return minimunHits;
    }

    /**
     * 
     * @param minimunHits
     *     The minimun_hits
     */
    public void setMinimunHits(Integer minimunHits) {
        this.minimunHits = minimunHits;
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
     *     The acumVotes
     */
    public Integer getAcumVotes() {
        return acumVotes;
    }

    /**
     * 
     * @param acumVotes
     *     The acum_votes
     */
    public void setAcumVotes(Integer acumVotes) {
        this.acumVotes = acumVotes;
    }

    /**
     * 
     * @return
     *     The countOpen
     */
    public Integer getCountOpen() {
        return countOpen;
    }

    /**
     * 
     * @param countOpen
     *     The count_open
     */
    public void setCountOpen(Integer countOpen) {
        this.countOpen = countOpen;
    }

    /**
     * 
     * @return
     *     The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * 
     * @param active
     *     The _active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * 
     * @return
     *     The delete
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * 
     * @param delete
     *     The _delete
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    /**
     * 
     * @return
     *     The campaignId
     */
    public String getCampaignId() {
        return campaignId;
    }

    /**
     * 
     * @param campaignId
     *     The campaign_id
     */
    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

}
