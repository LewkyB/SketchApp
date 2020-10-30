package com.example.jraw_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.oauth.TokenStore;
import net.dean.jraw.pagination.DefaultPaginator;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserAgent userAgent = new UserAgent("android", "github.com/lewkyb", "v1", "lookingfordriver");
        Credentials credentials = Credentials.userlessApp("uKb1rMTs_heYQA", UUID.randomUUID());
        NetworkAdapter networkAdapter = new OkHttpNetworkAdapter(userAgent);
        RedditClient redditClient = OAuthHelper.automatic(networkAdapter, credentials);
        DefaultPaginator<Submission> earthPorn = redditClient.subreddits("EarthPorn", "spaceporn").posts().build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> images = new ArrayList<String>();
                for (Submission s : earthPorn.next()) {
                    if (!s.isSelfPost() && s.getUrl().contains("i.imgur.com")) {
                        images.add(s.getUrl());
                    }
                }
                for (String s : images) {
                    System.out.println(s);
                }
            }
        });
    }
}
