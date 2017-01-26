package com.optimussoftware.api.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePhoto {

    @SerializedName("file")
    @Expose
    private Object file;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("content_url")
    @Expose
    private String contentUrl;

    /**
     * @return The file
     */
    public Object getFile() {
        return file;
    }

    /**
     * @param file The file
     */
    public void setFile(Object file) {
        this.file = file;
    }

    /**
     * @return The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return The filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename The filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return The contentUrl
     */
    public String getContentUrl() {
        return contentUrl;
    }

    /**
     * @param contentUrl The content_url
     */
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

}