package com.nikhil.vjitece.ui.home;

public class UploadHome {

    private String mImageUrl;

    public UploadHome() {
        //empty constructor needed
    }

    public UploadHome( String imageUrl) {

        mImageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        mImageUrl = imageUrl;
    }
}