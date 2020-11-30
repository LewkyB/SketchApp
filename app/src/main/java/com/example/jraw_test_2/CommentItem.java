package com.example.jraw_test_2;

public class CommentItem {
    private String mUsername;
    private String mCommentText;

    public CommentItem(String username, String commentText){
        mUsername = username;
        mCommentText = commentText;
    }

    public String getUsername(){
        return mUsername;
    }
    public String getCommentText(){
        return mCommentText;
    }
}
