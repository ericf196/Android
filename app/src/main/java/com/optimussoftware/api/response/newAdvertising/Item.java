
package com.optimussoftware.api.response.newAdvertising;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Image;

public class Item {

    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("source")
    @Expose
    private Source source;
    @SerializedName("interest_list")
    @Expose
    private List<InterestList> interestList = null;
    @SerializedName("count_likes")
    @Expose
    private Integer countLikes;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("acum_votes")
    @Expose
    private Integer acumVotes;
    @SerializedName("campaign_id")
    @Expose
    private CampaignId campaignId;
    @SerializedName("count_dislikes")
    @Expose
    private Integer countDislikes;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("count_votes")
    @Expose
    private Integer countVotes;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("trackeable")
    @Expose
    private Boolean trackeable;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("count_open")
    @Expose
    private Integer countOpen;

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public List<InterestList> getInterestList() {
        return interestList;
    }

    public void setInterestList(List<InterestList> interestList) {
        this.interestList = interestList;
    }

    public Integer getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(Integer countLikes) {
        this.countLikes = countLikes;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getAcumVotes() {
        return acumVotes;
    }

    public void setAcumVotes(Integer acumVotes) {
        this.acumVotes = acumVotes;
    }

    public CampaignId getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(CampaignId campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getCountDislikes() {
        return countDislikes;
    }

    public void setCountDislikes(Integer countDislikes) {
        this.countDislikes = countDislikes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountVotes() {
        return countVotes;
    }

    public void setCountVotes(Integer countVotes) {
        this.countVotes = countVotes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getTrackeable() {
        return trackeable;
    }

    public void setTrackeable(Boolean trackeable) {
        this.trackeable = trackeable;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getCountOpen() {
        return countOpen;
    }

    public void setCountOpen(Integer countOpen) {
        this.countOpen = countOpen;
    }

}
