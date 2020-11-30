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
import com.google.firebase.auth.FirebaseUser;
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
    private Bundle bundle = new Bundle();

    public MediaViewer (){
        super(viewer_media);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getIntent().hasExtra("image_url")) {
            String imgUrl = getIntent().getStringExtra("image_url");
            bundle.putString("image_url", imgUrl);

            DatabaseReference ref = FirebaseDatabase
                    .getInstance()
                    .getReference();

            final String[] postKey = new String[1];
            ref.child("posts")
                    .orderByChild("imageUrl")
                    .equalTo(imgUrl)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<?, ?> post = (HashMap<?, ?>) snapshot.getValue();
                            if(post!=null) {
                                for(Object key : post.keySet()){
                                    postKey[0] = key.toString();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(false)
                    .add(R.id.f_image, com.example.jraw_test_2.ImageFragment.class, bundle)
                    .commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(false)
                    .add(R.id.f_comment, com.example.jraw_test_2.CommentFragment.class, bundle)
                    .commit();


            newComment = (EditText)findViewById(R.id.newComment);
            newComment.setOnKeyListener(new EditText.OnKeyListener(){
                public boolean onKey(View v, int keyCode, KeyEvent event){
                    if(keyCode == 66 && event.getAction() != KeyEvent.ACTION_UP){
                        commentString = String.valueOf(newComment.getText());
                        addComment(commentString, postKey[0]);
                        newComment.getText().clear();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(false)
                                .replace(R.id.f_comment, com.example.jraw_test_2.CommentFragment.class, bundle)
                                .commit();
                        return true;
                    }
                    return false;
                }
            });

        }
    }
    private void addComment(String comment, String postKey){
        String currentUser = null;
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        if(mAuth != null){
            currentUser = mAuth.getEmail();
        }
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference("posts");

        if (currentUser != null) {
            ref.child(postKey)
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
        }else{
            Toast.makeText(mContext, "You must login before commenting!", Toast.LENGTH_LONG).show();
        }
    }
}