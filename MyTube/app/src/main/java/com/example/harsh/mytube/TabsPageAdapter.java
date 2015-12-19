package com.example.harsh.mytube;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by harsh on 10/17/2015.
 */
public class TabsPageAdapter extends FragmentPagerAdapter {

    public TabsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                // Top Rated fragment activity
                return new SearchActivity();

            case 1:
                // Games fragment activity
                return new SavedPlaylistActivity();



        }

        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }
}
