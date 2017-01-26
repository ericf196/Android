package com.optimussoftware.api.response.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Logo {

    @SerializedName("content_url")
    @Expose
    private String contentUrl;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("file")
    @Expose
    private String file;

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

}
