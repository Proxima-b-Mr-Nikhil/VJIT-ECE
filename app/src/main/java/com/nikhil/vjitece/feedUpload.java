package com.nikhil.vjitece;

import java.net.URL;

public class feedUpload {
    private String name;
    private String imageUrl;
    private String time;

    public feedUpload() {
        //empty constructor needed
    }

    public feedUpload(String name, String imageUrl,String time,String videourl) {

       this. name = name;
        this.time=time;
       this. imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}