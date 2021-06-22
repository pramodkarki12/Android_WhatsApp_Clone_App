package com.pramodkarki.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pramodkarki.myapplication.adapters.FragmentAdapter;
import com.pramodkarki.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /* firebase authentication */
        auth = FirebaseAuth.getInstance();

        /* set the custom FragmentAdapter with viewPager */
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        /* Similarly, set the viewPager with the tabLayout */
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    /* Override the onCreateOptionsMenu
        to create the menu that we have just created */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* if user selected any of the menu item,
       then perform what output will it generate? */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /* item selection will be performed through its Id */
        switch (item.getItemId()) {

            case R.id.settings:
                Intent intentSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentSettings);
                break;

            case R.id.logout:
                /* logout from the existing account,
                  and display the SignIn Activity */
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                break;

            case R.id.groupChat:
                Intent intentGroupChat = new Intent(MainActivity.this, GroupChatActivity.class);
                startActivity(intentGroupChat);
                break;
        }
        return true;
    }
}