package com.example.jraw_test_2;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jraw_test_2.Browser.CommentAdapter;
import com.example.jraw_test_2.Browser.CommentItem;

import java.util.ArrayList;

public class CommentFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public CommentFragment() {
        super(R.layout.fragment_comments);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<CommentItem> commentList = new ArrayList<>();
        //Get Firebase Comments table and add to commentList
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Gabriel", "WOW this is an amazing situation."));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Willer", "I'm ganna freeze you and you jump in soon."));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Gus", "It was a gift, so dangerous. But I'm still interested"));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Gabriel", "WOW this is an amazing situation."));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Willer", "I'm ganna freeze you and you jump in soon."));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Gus", "It was a gift, so dangerous. But I'm still interested"));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Gabriel", "WOW this is an amazing situation."));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Willer", "I'm ganna freeze you and you jump in soon."));
        commentList.add(new CommentItem(R.drawable.ic_baseline_person_24, "Gus", "It was a gift, so dangerous. But I'm still interested"));

        mRecyclerView = view.findViewById(R.id.commentRecycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new CommentAdapter(commentList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }
}