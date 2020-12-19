package com.truck.transfly.Model;

public class ReferModel {

    private boolean isText,isTextImages,isImages;

    private String image_url,text;

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public boolean isTextImages() {
        return isTextImages;
    }

    public void setTextImages(boolean textImages) {
        isTextImages = textImages;
    }

    public boolean isImages() {
        return isImages;
    }

    public void setImages(boolean images) {
        isImages = images;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
