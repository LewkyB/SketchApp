package com.example.jraw_test_2;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class ImageFragment extends Fragment {

    public ImageFragment(){
        super(R.layout.fragment_image);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        String imageURL = requireArguments().getString("image_url");
//        ImageView image = view.findViewById(R.id.viewer_image);
//        Picasso.with(getContext())
//                .load(imageURL)
//                .noFade()
//                .into(image);
    }
}
