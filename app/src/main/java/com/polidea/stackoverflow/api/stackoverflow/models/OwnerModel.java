package com.polidea.stackoverflow.api.stackoverflow.models;

/**
 * Created by Hubert on 27.09.2014.
 */
public class OwnerModel {
    private String UserID;
    private String ImageURL;
    private String UserName;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
