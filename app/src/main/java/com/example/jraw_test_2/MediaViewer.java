package com.example.jraw_test_2;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.example.jraw_test_2.R.layout.viewer_media;

public class MediaViewer extends AppCompatActivity {
    private static final String TAG = "MediaViewer";
    private Context mContext = this;
    private FirebaseAuth mAuth;

    private EditText newComment;
    private String commentString;

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
            DatabaseReference ref = FirebaseDatabase
                    .getInstance()
                    .getReference();

            final String[] postId = new String[1];
            ref.child("posts")
                    .orderByChild("imageUrl")
                    .equalTo(imgUrl)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<?, ?> post = (HashMap<?, ?>) snapshot.getValue();
                            if(post!=null) {
                                for(Object key : post.keySet()){
                                    postId[0] = key.toString();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            newComment = (EditText)findViewById(R.id.newComment);
            newComment.setOnKeyListener(new EditText.OnKeyListener(){
                public boolean onKey(View v, int keyCode, KeyEvent event){
                    if(keyCode == 66 && event.getAction() != KeyEvent.ACTION_UP){
                        commentString = String.valueOf(newComment.getText());
                        addComment(commentString, postId[0]);
                        newComment.getText().clear();
                        return true;
                    }
                    return false;
                }
            });
            bundle.putString("postId", postId[0]);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.f_image, com.example.jraw_test_2.ImageFragment.class, bundle)
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.f_comment, com.example.jraw_test_2.CommentFragment.class, bundle)
                    .commit();

        }
    }
    private void addComment(String comment, String postId){
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("posts");

        if (currentUser != null) {
            ref.child(postId)
                    .child("comments")
                    .push()
                    .setValue(new CommentItem(currentUser, comment))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mContext, "Comment Added Successfully!", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(mContext, "Failed to Add Comment!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}