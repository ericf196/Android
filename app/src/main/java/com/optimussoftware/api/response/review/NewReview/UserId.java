
package com.optimussoftware.api.response.review.NewReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserId {

    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("is_banned")
    @Expose
    private Boolean isBanned;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("profile_photo")
    @Expose
    private ProfilePhoto profilePhoto;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("social_photo")
    @Expose
    private String socialPhoto;
    @SerializedName("social_id")
    @Expose
    private String socialId;
    @SerializedName("link")
    @Expose
    private String link;


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
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 
     * @param birthday
     *     The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 
     * @return
     *     The isBanned
     */
    public Boolean getIsBanned() {
        return isBanned;
    }

    /**
     * 
     * @param isBanned
     *     The is_banned
     */
    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * @param fullName
     *     The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
     *     The profilePhoto
     */
    public ProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * 
     * @param profilePhoto
     *     The profile_photo
     */
    public void setProfilePhoto(ProfilePhoto profilePhoto) {
        this.profilePhoto = profilePhoto;
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
     *     The source
     */
    public String getSource() {
        return source;
    }

    /**
     * 
     * @param source
     *     The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * @return
     *     The salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 
     * @param salt
     *     The salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
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
     * The socialPhoto
     */
    public String getSocialPhoto() {
        return socialPhoto;
    }

    /**
     *
     * @param socialPhoto
     * The social_photo
     */
    public void setSocialPhoto(String socialPhoto) {
        this.socialPhoto = socialPhoto;
    }

    /**
     *
     * @return
     * The socialId
     */
    public String getSocialId() {
        return socialId;
    }

    /**
     *
     * @param socialId
     * The social_id
     */
    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    /**
     *
     * @return
     * The link
     */
    public String getLink() {
        return link;
    }

    /**
     *
     * @param link
     * The link
     */
    public void setLink(String link) {
        this.link = link;
    }
}
