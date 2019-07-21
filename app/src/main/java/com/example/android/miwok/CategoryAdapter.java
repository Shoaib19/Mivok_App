package com.example.android.miwok;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class CategoryAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Colors", "Family", "Phrases", "Numbers" };

    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new ColorsFragment();
        } else if (i == 1) {
            return new FamilyFragment();
        } else if (i == 2) {
            return new PhrasesFragment();
        } else {
            return new NumbersFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return 4;
    }
}
