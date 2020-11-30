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
    private String postKey;

    public CommentFragment() { super(R.layout.fragment_comments); }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) {
            postKey = savedInstanceState.getString("postId");
        }

        //Get Firebase Comments table and add to commentList
        ArrayList<CommentItem> commentList = getComments(postKey);;

        mRecyclerView = view.findViewById(R.id.commentRecycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CommentAdapter(commentList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    private ArrayList<CommentItem> getComments(String postKey){
        ArrayList<CommentItem> returnList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("posts");
        ref.orderByChild("postId")
                .equalTo(postKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot i : snapshot.getChildren()){
                            HashMap<?,?> iTop = (HashMap<?,?>) i.getValue();
                            if(iTop != null) {
                                HashMap<?,?> commentHash = (HashMap<?, ?>) iTop.get("comments");
                                if(commentHash != null){
                                    for(Object key : commentHash.keySet()) {
                                        HashMap<?, ?> hash = (HashMap<?, ?>) commentHash.get(key);
                                        returnList.add(new CommentItem(
                                            hash.get("username").toString(),
                                            hash.get("commentText").toString()));
                                    }
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