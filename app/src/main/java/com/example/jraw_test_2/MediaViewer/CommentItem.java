package com.example.jraw_test_2.Browser;

public class CommentItem {
    private int mImageResource;
    private String mUsername;
    private String mCommentText;

    public CommentItem(int imageResource, String username, String commentText){
        mImageResource = imageResource;
        mUsername = username;
        mCommentText = commentText;
    }

    public int getImageResource(){
        return mImageResource;
    }
    public String getUsername(){
        return mUsername;
    }
    public String getCommentText(){
        return mCommentText;
    }
}
