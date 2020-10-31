package com.example.jraw_test_2;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// https://www.youtube.com/watch?v=mMzT4fSHU-8&list=PLrnPJCHvNZuBCiCxN8JPFI57Zhr5SusRL&index=3

public class Adapter extends RecyclerView.Adapter <Adapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public imageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);
        }
    }
}
