package com.example.jraw_test_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button open_reddit_browser_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        open_reddit_browser_button = (Button) findViewById(R.id.open_reddit_browser_button);
        open_reddit_browser_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_RedditViewer_activity();
            }
        });
    }

    public void open_RedditViewer_activity() {
        Intent intent = new Intent(this, RedditViewer.class);
        startActivity(intent);
    }
}
