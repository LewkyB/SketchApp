package com.example.jraw_test_2;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


public class ImageFragment extends Fragment {

    private PhotoView photoView;

    public ImageFragment(){
        super(R.layout.fragment_image);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        String imageURL = requireArguments().getString("image_url");
        addImage(view, imageURL);
    }

    private void addImage(View view, String imgUrl){
        photoView = view.findViewById(R.id.viewer_image);
        Glide.with(getContext())
                .load(imgUrl)
                .into(photoView);
    }
}
