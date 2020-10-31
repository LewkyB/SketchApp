package com.example.jraw_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
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

    TextView textLoad, textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        textLoad = findViewById(R.id.textLoad);
        textMessage = findViewById(R.id.textMessage);
        textLoad.setText("Loading...");
        new MyTask().execute();


    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String result;

        @Override
        protected Void doInBackground(Void... voids) {

            UserAgent userAgent = new UserAgent("android", "github.com/lewkyb", "v1", "lookingfordriver");
            Credentials credentials = Credentials.userlessApp("uKb1rMTs_heYQA", UUID.randomUUID());
            NetworkAdapter networkAdapter = new OkHttpNetworkAdapter(userAgent);
            RedditClient redditClient = OAuthHelper.automatic(networkAdapter, credentials);
            DefaultPaginator<Submission> earthPorn = redditClient.subreddits("EarthPorn", "spaceporn").posts().build();

            Listing<Submission> submissions = earthPorn.next();
            for (Submission s : submissions) {
                System.out.println(s.getUrl());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textMessage.setText(result);
            textLoad.setText("Finished");
            super.onPostExecute(aVoid);
        }

    }

}
