package com.example.jraw_test_2;

import android.os.AsyncTask;
import android.util.Log;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;

import java.util.ArrayList;
import java.util.UUID;

public class RedditLoader {

    private static final String TAG = "RedditLoader";

    public UserAgent userAgent;
    public static Credentials credentials;
    public static NetworkAdapter networkAdapter;
    public static RedditClient redditClient;
    public static DefaultPaginator<Submission> subreddit;
    public static Listing<Submission> submissions;

    public String subreddit_name;
    public ArrayList<Item> redditItemList = new ArrayList<>();

    public RedditLoader() {
    }

    public ArrayList<Item> getItemList() {
        return redditItemList;
    }

    public class createListTask extends AsyncTask<Void, Void, Void> {
        @Override
        public Void doInBackground(Void... voids) {
            Log.d(TAG, "starting createListTask AsyncTask");

            userAgent = new UserAgent("android", "github.com/lewkyb", "v1", "lookingfordriver");
            credentials = Credentials.userlessApp("uKb1rMTs_heYQA", UUID.randomUUID());
            networkAdapter = new OkHttpNetworkAdapter(userAgent);
            redditClient = OAuthHelper.automatic(networkAdapter, credentials);
            subreddit = redditClient.subreddit("cats").posts().build();

            submissions = subreddit.next();
            for (Submission s : submissions) {

                if (!s.isSelfPost() && s.getUrl().contains("jpg")) {

                    String imageUrl = s.getUrl();       // URL
                    System.out.println(imageUrl);
                    String postTitle = s.getTitle();    // Post Title
                    int likeCount = s.getScore();       // Upvotes - Downvotes = Score

                    // add data to Item object
                    redditItemList.add(new Item(imageUrl, postTitle, likeCount));
                }
            }
            return null;
        }
    }
    public class getMorePosts extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "starting getMorePosts AsyncTask");

            submissions = subreddit.next();
            for (Submission s : submissions) {

                if (!s.isSelfPost() && s.getUrl().contains("jpg")) {

                    String imageUrl = s.getUrl();       // URL
                    System.out.println(imageUrl);
                    String postTitle = s.getTitle();    // Post Title
                    int likeCount = s.getScore();       // Upvotes - Downvotes = Score

                    // add data to Item object
                    redditItemList.add(new Item(imageUrl, postTitle, likeCount));
                }
            }

            return null;
        }
    }


}
