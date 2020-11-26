package com.example.jraw_test_2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/*

    Purpose: get list and display to recyclerview with Glide

    see also: https://stackoverflow.com/questions/3674951/whats-the-role-of-adapters-in-android

 */
public class Adapter extends RecyclerView.Adapter <Adapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Item> mItemList;

    public Adapter(Context context, ArrayList <Item> list) {
        mContext = context;
        mItemList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = mItemList.get(position);

        String imageUrl = currentItem.getImageUrl();

        // use Glide to pull and show image on card
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .into(holder.mImageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(mContext, MediaViewer.class);
                intent.putExtra("image_url", imageUrl);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }


    }
}
