package com.example.jraw_test_2;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import static com.example.jraw_test_2.R.layout.viewer_media;

public class MediaViewer extends AppCompatActivity {

    private final Context context = MediaViewer.this;

    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(viewer_media);
        getNewIntent();
    }

    private void getNewIntent(){
        if(getIntent().hasExtra("image_url")) {
            String imgUrl = getIntent().getStringExtra("image_url");
            setImage(imgUrl);
        }
    }

    private void setImage(String imageUrl){
        ImageView image = findViewById(R.id.viewer_image);
        Picasso.with(context)
                .load(imageUrl)
                .noFade()
                .into(image);
    }
}