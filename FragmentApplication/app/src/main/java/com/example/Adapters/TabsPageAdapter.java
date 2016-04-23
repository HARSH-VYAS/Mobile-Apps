package com.example.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.harshpvyas.fragmentapplication.GamesFragment;
import com.example.harshpvyas.fragmentapplication.MoviesFragment;
import com.example.harshpvyas.fragmentapplication.TopRatedFragment;

/**
 * Created by Harsh P Vyas on 3/29/2016.
 */
public class TabsPageAdapter extends FragmentPagerAdapter{

    public TabsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

     switch (position){
         case 0:
                return new TopRatedFragment();
         case 1:
             return new GamesFragment();
         case 2:
             return new MoviesFragment();
     }



        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
