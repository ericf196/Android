package com.optimussoftware.boohos.showImage;


import java.io.Serializable;

/**
 * Created by guerra on 14/07/16.
 * Clase para enviar los datos a fullImage
 */
public class ImageDescription implements Serializable {

    private String name;
    private String description;
    private String urlImage;

    public ImageDescription(String name, String description, String urlImage) {
        this.name = name;
        this.description = description;
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
