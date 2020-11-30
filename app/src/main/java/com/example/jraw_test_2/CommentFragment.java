package com.example.jraw_test_2;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String imageUrl;
    private ArrayList<CommentItem> commentList = new ArrayList<>();


    public CommentFragment() { super(R.layout.fragment_comments); }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageUrl = requireArguments().getString("image_url");
        createCommentView(view, imageUrl);
    }

    private void createCommentView(View view, String imageUrl){
        //Get Firebase Comments table and add to commentList
        commentList = getComments(imageUrl);
        mRecyclerView = view.findViewById(R.id.commentRecycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CommentAdapter(commentList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<CommentItem> getComments(String imageUrl){
        ArrayList<CommentItem> returnList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("posts");

        ref.orderByChild("imageUrl")
                .equalTo(imageUrl)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot i : snapshot.getChildren()) {
                            HashMap<?, ?> snapHash = (HashMap<?, ?>) i.getValue();
                            HashMap<?, ?> commentHash = (HashMap<?, ?>) snapHash.get("comments");
                            if(commentHash != null) {
                                for (Object key : commentHash.keySet()) {
                                    HashMap<?, ?> comments = (HashMap<?, ?>) commentHash.get(key.toString());
                                    returnList.add(new CommentItem(
                                            comments.get("username").toString(),
                                            comments.get("commentText").toString()));
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        return returnList;
    }
}