package com.nikhil.vjitece.ui.dashboard;

class Upload {
    private String mName;

    private  String message;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String mName, String message) {
        this.mName = mName;
        this.message = message;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
