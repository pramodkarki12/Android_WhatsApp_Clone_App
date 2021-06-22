package com.pramodkarki.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pramodkarki.myapplication.databinding.ActivitySettingsBinding;
import com.pramodkarki.myapplication.models.Users;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /* update the userName and about of an user,
            and store it into firebaseDatabase */
        binding.saveUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.etUserName.getText().toString();
                String status = binding.etStatus.getText().toString();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("userName", userName);
                obj.put("status", status);

                database.getReference()
                        .child("Users")
                        .child(auth.getUid())
                        .updateChildren(obj);

                Toast.makeText(getApplicationContext(), "Profile Updated !!!", Toast.LENGTH_SHORT).show();
            }
        });

        /* set the updated profile img, userName, and status from the firebaseDatabase */
        database.getReference().child("Users")
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);

                        /* get the profile picture from the database,
                            and change the user profile image */
                        Picasso.get()
                                .load(users.getProfilePic())
                                .placeholder(R.drawable.ic_avatar)
                                .into(binding.profileImage);

                        /* set the updated userName and about/status of an user.
                            Extract the information from the firebaseDatabase */
                        binding.etStatus.setText(users.getStatus());
                        binding.etUserName.setText(users.getUserName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        /* upload the profile image into firebaseStorage */
        binding.addProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // replace with it, ("*/*") for including all type of file i.e., mp4, video, docs, pdf
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Select Images from Phone Internal Storage */
        if (data.getData() != null) {
            Uri sFile = data.getData();
            binding.profileImage.setImageURI(sFile);

            /* store the image into firebaseStorage */
            final StorageReference reference = storage.getReference()
                    .child("profile_pictures")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(sFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    /* attach the stored profileImg URL into the "Users" database named with "profileImgUrl" */
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users")
                                    .child(auth.getUid())
                                    .child("profileImgUrl")
                                    .setValue(uri.toString());

                            Toast.makeText(SettingsActivity.this, "Image Uploaded Successfully !!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}