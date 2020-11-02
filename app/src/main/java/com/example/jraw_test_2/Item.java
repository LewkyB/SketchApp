package com.example.jraw_test_2;

/*

    Purpose: Store information taken from JRAW

 */
public class Item {

    private String mImageUrl;
    private String mTitle;
    private int mLikes;

    public Item(String imageUrl, String title, int likes) {
        mImageUrl = imageUrl;
        mTitle = title;
        mLikes = likes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getLikeCount() {
        return mLikes;
    }
}
