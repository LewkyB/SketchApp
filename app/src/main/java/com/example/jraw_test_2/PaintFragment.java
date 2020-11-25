package com.example.jraw_test_2;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class PaintFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "PaintFragment";

    FirebaseAuth mAuth;

    PaintView paintView;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "PaintFragment onCreateView()");

        View view = inflater.inflate(R.layout.fragment_paint, container, false);

        paintView = view.findViewById(R.id.PaintView);

        // used for testing upload functionality
        button = view.findViewById(R.id.upload_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeBitmapFirebase();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "PaintFragment onCreate()");
        mAuth = FirebaseAuth.getInstance(); // start FirebaseAuth
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "PaintFragment onStart()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "PaintFragment onDestroy()");
    }

    // takes image drawn on canvas as a bitmap, converts to jpg, then uploads to firebase storage
    public void storeBitmapFirebase() {
        Log.d(TAG, "starting storeBitmapFirebase()");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://jrawtest.appspot.com");

        // create random UUID for unique file name
        String randomUUID = UUID.randomUUID().toString();

        // Create a reference to "mountains.jpg"
        StorageReference imageRef = storageRef.child(randomUUID);

        // Create a reference to 'images/mountains.jpg'
        StorageReference userImagesRef = storageRef.child("user_images/" + randomUUID);

        // While the file names are the same, the references point to different files
        imageRef.getName().equals(userImagesRef.getName());    // true
        imageRef.getPath().equals(userImagesRef.getPath());    // false

        // get bitmap from paintView
        paintView.setDrawingCacheEnabled(true);
        paintView.buildDrawingCache();
        Bitmap bmp = paintView.getDrawingCache();

        // convert image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // send image to firebase storage
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "Firebase storeBitmap:failure");
                Toast.makeText(getContext(), "Upload Failure!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "Firebase storeBitmap:success");
                Toast.makeText(getContext(), "Upload Success!", Toast.LENGTH_LONG).show();

                // add image's randomUUID to list of images the user has submitted
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("imageList").push().setValue(randomUUID)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Firebase storeBitmapFirebase add image to user's image list:success");
                        } else {
                            Log.d(TAG, "Firebase storeBitmapFirebase add image to user's image list:failure");
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        // forced to implement this method by IDE
    }
}