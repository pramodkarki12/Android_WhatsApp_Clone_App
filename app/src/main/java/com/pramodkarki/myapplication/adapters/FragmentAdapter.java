package com.pramodkarki.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pramodkarki.myapplication.fragments.CallFragment;
import com.pramodkarki.myapplication.fragments.ChatFragment;
import com.pramodkarki.myapplication.fragments.StatusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        /* determine the position of fragment being used */
        switch (position) {
            case 0:
                return new ChatFragment();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallFragment();
            default:
                return new ChatFragment();
        }
    }

    /* determine the number of fragment being used */
    @Override
    public int getCount() {
        return 3;
    }

    /* define the title for each fragment */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        /* Here, "position" defines which fragment being used */
        String title = null;
        if (position == 0) {
            title = "CHATS";
        } else if (position == 1) {
            title = "STATUS";
        } else if (position == 2) {
            title = "CALLS";
        }
        return title;
    }
}
