package com.example.jraw_test_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<Item> mItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // setup recycle viewer
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); // saves memory because size doesn't change
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // instantiate list?
        mItemList = new ArrayList<>();

        new MyTask().execute();
    }

    /*
        This class is created to allow for networking on the MainActivity thread.

        Sets up all OAuth2 for Reddit API using JRAW. Using JRAW it creates an object
        that contains subreddit and post data.
     */
    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            // client setup
            UserAgent userAgent = new UserAgent("android", "github.com/lewkyb", "v1", "lookingfordriver");
            Credentials credentials = Credentials.userlessApp("uKb1rMTs_heYQA", UUID.randomUUID());
            NetworkAdapter networkAdapter = new OkHttpNetworkAdapter(userAgent);
            RedditClient redditClient = OAuthHelper.automatic(networkAdapter, credentials);

            DefaultPaginator<Submission> earthPorn = redditClient.subreddits("aww", "spaceporn").posts().build();

            Listing<Submission> submissions = earthPorn.next();
            for (Submission s : submissions) {

                String imageUrl = s.getUrl();       // URL
                String postTitle = s.getTitle();    // Post Title
                int likeCount = s.getScore();       // Upvotes - Downvotes = Score

                // add data to Item object
                mItemList.add(new Item(imageUrl, postTitle, likeCount));
            }

            // not clear on what adapters do yet
            mAdapter = new Adapter(MainActivity.this, mItemList);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            /*
                attempted to run this within the doInBackground(), but
                it gave the error:

                    android.view.ViewRootImpl$CalledFromWrongThreadException: Only the
                    original thread that created a view hierarchy can touch its views

                moving this line here fixed the issue, but why? scope?
             */
            mRecyclerView.setAdapter(mAdapter);

            super.onPostExecute(aVoid);
        }

    }

}
