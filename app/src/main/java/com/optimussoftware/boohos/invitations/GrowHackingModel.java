package com.optimussoftware.boohos.invitations;

import android.net.Uri;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;

/**
 * Created by leonardojpr on 12/7/16.
 */

public class GrowHackingModel {

    private String name;
    private String country;
    private Uri img_profile;
    private boolean isSelect;

    public GrowHackingModel(String name, String country, Uri img_profile) {
        this.name = name;
        this.country = country;
        this.img_profile = img_profile;
    }

    public GrowHackingModel(boolean isSelect, Uri img_profile, String country, String name) {
        this.isSelect = isSelect;
        this.img_profile = img_profile;
        this.country = country;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Uri getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(Uri img_profile) {
        this.img_profile = img_profile;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
