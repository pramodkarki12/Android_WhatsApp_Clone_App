package com.pramodkarki.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pramodkarki.myapplication.R;
import com.pramodkarki.myapplication.adapters.UserAdapter;
import com.pramodkarki.myapplication.databinding.FragmentChatBinding;
import com.pramodkarki.myapplication.models.Users;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    FragmentChatBinding binding;
    FirebaseDatabase database;
    ArrayList<Users> userList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false);

        /* Initialize the firebase database */
        database = FirebaseDatabase.getInstance();

        UserAdapter adapter = new UserAdapter(userList, getContext());
        binding.chatRecycleView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecycleView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());

                    /* Don't show the profile/User of an loggedIn user */
                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid())) {
                        userList.add(users);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}