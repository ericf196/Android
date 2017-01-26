package com.optimussoftware.api.response.campaign.NewCampaign;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Campaign {

    @SerializedName("finish_date")
    @Expose
    private String finishDate;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;
    @SerializedName("locations")
    @Expose
    private List<String> locations = new ArrayList<String>();
    @SerializedName("_user_create")
    @Expose
    private String userCreate;
    @SerializedName("min_age")
    @Expose
    private Integer minAge;
    @SerializedName("target_gender")
    @Expose
    private String targetGender;
    @SerializedName("end_hour")
    @Expose
    private Integer endHour;
    @SerializedName("start_hour")
    @Expose
    private Integer startHour;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("languages")
    @Expose
    private List<String> languages = new ArrayList<String>();
    @SerializedName("interest_list")
    @Expose
    private List<String> interestList = new ArrayList<String>();
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("max_age")
    @Expose
    private Integer maxAge;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("days_efective")
    @Expose
    private List<String> daysEfective = new ArrayList<String>();
    @SerializedName("_type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The finishDate
     */
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * 
     * @param finishDate
     *     The finish_date
     */
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

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
     *     The customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 
     * @param customerId
     *     The customer_id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
     *     The locations
     */
    public List<String> getLocations() {
        return locations;
    }

    /**
     * 
     * @param locations
     *     The locations
     */
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    /**
     * 
     * @return
     *     The userCreate
     */
    public String getUserCreate() {
        return userCreate;
    }

    /**
     * 
     * @param userCreate
     *     The _user_create
     */
    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    /**
     * 
     * @return
     *     The minAge
     */
    public Integer getMinAge() {
        return minAge;
    }

    /**
     * 
     * @param minAge
     *     The min_age
     */
    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    /**
     * 
     * @return
     *     The targetGender
     */
    public String getTargetGender() {
        return targetGender;
    }

    /**
     * 
     * @param targetGender
     *     The target_gender
     */
    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender;
    }

    /**
     * 
     * @return
     *     The endHour
     */
    public Integer getEndHour() {
        return endHour;
    }

    /**
     * 
     * @param endHour
     *     The end_hour
     */
    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    /**
     * 
     * @return
     *     The startHour
     */
    public Integer getStartHour() {
        return startHour;
    }

    /**
     * 
     * @param startHour
     *     The start_hour
     */
    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
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
     *     The languages
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * 
     * @param languages
     *     The languages
     */
    public void setLanguages(List<String> languages) {
        this.languages = languages;
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
     *     The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The maxAge
     */
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * 
     * @param maxAge
     *     The max_age
     */
    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
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
     *     The daysEfective
     */
    public List<String> getDaysEfective() {
        return daysEfective;
    }

    /**
     * 
     * @param daysEfective
     *     The days_efective
     */
    public void setDaysEfective(List<String> daysEfective) {
        this.daysEfective = daysEfective;
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

}
