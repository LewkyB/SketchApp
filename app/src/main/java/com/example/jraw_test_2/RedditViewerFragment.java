package com.example.jraw_test_2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class RedditViewerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<Item> mItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reddit_viewer, container, false);

        // setup recycle viewer
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); // saves memory because size doesn't change
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // instantiate list?
        mItemList = new ArrayList<>();

        new RedditViewerFragment.MyTask().execute();
    }

    // This task is created to allow for networking on the MainActivity thread.
    //
    // Sets up all OAuth2 for Reddit API using JRAW. Using JRAW it creates an object
    // that contains subreddit and post data.
    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            // JRAW client setup
            UserAgent userAgent = new UserAgent("android", "github.com/lewkyb", "v1", "lookingfordriver");
            Credentials credentials = Credentials.userlessApp("uKb1rMTs_heYQA", UUID.randomUUID());
            NetworkAdapter networkAdapter = new OkHttpNetworkAdapter(userAgent);
            RedditClient redditClient = OAuthHelper.automatic(networkAdapter, credentials);

            // TODO: allow for user to choose subreddits
            DefaultPaginator<Submission> earthPorn = redditClient.subreddits("aww", "spaceporn").posts().build();

            Listing<Submission> submissions = earthPorn.next();
            for (Submission s : submissions) {

                // TODO: avoid pulling gif or video
                if (!s.isSelfPost() && s.getUrl().contains("jpg")) {

                    String imageUrl = s.getUrl();       // URL
                    String postTitle = s.getTitle();    // Post Title
                    int likeCount = s.getScore();       // Upvotes - Downvotes = Score

                    // add data to Item object
                    mItemList.add(new Item(imageUrl, postTitle, likeCount));
                }
            }

            // send list to RecyclerView for display
            mAdapter = new Adapter(getContext(), mItemList);

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