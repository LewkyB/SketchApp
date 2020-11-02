package com.example.jraw_test_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // TODO: fix all buttons, make sure they go where they are supposed to

    // TODO: canvas page
    // TODO: profile page

    // RedditViewer
    // TODO: have the RedditViewer data persist when switching between fragments
    // TODO: enlarge card and offer comment section with upvote/downvote

    // LOGIN
    // TODO: have login screen go to another screen with registering/logging in
    // TODO: separate out login and registration?
    // TODO: skip button?

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // used for changing fragments with bottomNav selection
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // set default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new LoginFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_browser:
                            selectedFragment = new RedditViewerFragment();
                            break;
                        case R.id.nav_canvas:
                            // TODO: implement canvas
//                            selectedFragment = new CanvasFragment();
//                            break;
                        case R.id.nav_profile:
                            // TODO: create profile and switch LoginFragment() with ProfileFragment()
                            selectedFragment = new LoginFragment();
                            break;
                    }

                    // sets fragment based on case
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    // true means that we want to select the clicked item
                    // if false, the fragment would still be displayed, but not selected
                    return true;
                }
            };

}
