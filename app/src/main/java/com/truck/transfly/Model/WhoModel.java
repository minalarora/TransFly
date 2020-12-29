package com.truck.transfly.Model;

public class WhoModel {

    private String OwnerType,belowOwner,below2Owner,keyword;

    private int image_url;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOwnerType() {
        return OwnerType;
    }

    public void setOwnerType(String ownerType) {
        OwnerType = ownerType;
    }

    public int getImage_url() {
        return image_url;
    }

    public void setImage_url(int image_url) {
        this.image_url = image_url;
    }

    public String getBelowOwner() {
        return belowOwner;
    }

    public void setBelowOwner(String belowOwner) {
        this.belowOwner = belowOwner;
    }

    public String getBelow2Owner() {
        return below2Owner;
    }

    public void setBelow2Owner(String below2Owner) {
        this.below2Owner = below2Owner;
    }
}
