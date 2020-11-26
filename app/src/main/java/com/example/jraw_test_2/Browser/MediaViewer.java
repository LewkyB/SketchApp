package com.example.jraw_test_2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.jraw_test_2.R.layout.viewer_media;

public class MediaViewer extends AppCompatActivity {

    public MediaViewer (){
        super(viewer_media);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("image_url")) {
            String imgUrl = getIntent().getStringExtra("image_url");
            Bundle bundle = new Bundle();
            bundle.putString("image_url", imgUrl);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.f_image, ImageFragment.class, bundle)
                    .commit();
        }
    }
}