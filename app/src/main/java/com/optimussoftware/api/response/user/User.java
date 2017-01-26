package com.optimussoftware.api.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.optimussoftware.api.response.commons.Links;

import java.util.Date;
import java.util.List;

public class User {

    @SerializedName("salt")
    @Expose
    private String salt;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("is_banned")
    @Expose
    private Boolean isBanned;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("profile_photo")
    @Expose
    private ProfilePhoto profilePhoto;
    @SerializedName("social_photo")
    @Expose
    private String socialPhoto;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("_updated")
    @Expose
    private String updated;
    @SerializedName("_etag")
    @Expose
    private String etag;
    @SerializedName("_created")
    @Expose
    private String created;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("birthday")
    @Expose
    private Date birthday;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("wishes")
    @Expose
    private List<Wish> wishes = null;
    @SerializedName("source")

    /**
     * @return The salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt The salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * @param links The _links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     * @return The isBanned
     */
    public Boolean getIsBanned() {
        return isBanned;
    }

    /**
     * @param isBanned The is_banned
     */
    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The _type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The profilePhoto
     */
    public ProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * @param socialPhoto The profile_photo
     */
    public void setSocialPhoto(String socialPhoto) {
        this.socialPhoto = socialPhoto;
    }

    /**
     * @return The socialPhoto
     */
    public String getSocialPhoto() {
        return socialPhoto;
    }

    /**
     * @param profilePhoto The profile_photo
     */
    public void setProfilePhoto(ProfilePhoto profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The updated
     */
    public String getUpdated() {
        return updated;
    }

    /**
     * @param updated The _updated
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     * @return The etag
     */
    public String getEtag() {
        return etag;
    }

    /**
     * @param etag The _etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }

    /**
     * @return The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * @param created The _created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }


    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @param gender The source
     */
    public void setGender(String gender) {
        this.gender = gender;
    }


    /**
     * @return The birthday
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param birthday The source
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    /**
     * @return The birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }
}