
package com.optimussoftware.api.response.newAdvertising;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampaignId {

    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("end_hour")
    @Expose
    private Integer endHour;
    @SerializedName("days_efective")
    @Expose
    private List<String> daysEfective = null;
    @SerializedName("target_gender")
    @Expose
    private String targetGender;
    @SerializedName("start_hour")
    @Expose
    private Integer startHour;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("min_age")
    @Expose
    private Integer minAge;
    @SerializedName("finish_date")
    @Expose
    private String finishDate;
    @SerializedName("_active")
    @Expose
    private Boolean active;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;
    @SerializedName("max_age")
    @Expose
    private Integer maxAge;
    @SerializedName("_delete")
    @Expose
    private Boolean delete;

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public List<String> getDaysEfective() {
        return daysEfective;
    }

    public void setDaysEfective(List<String> daysEfective) {
        this.daysEfective = daysEfective;
    }

    public String getTargetGender() {
        return targetGender;
    }

    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

}
