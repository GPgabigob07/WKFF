package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.currentPlayer;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.gpginc.ntateam.apptest.CurrentPlayer;

import java.util.ArrayList;
import java.util.List;

public class CurrentPlayerTabsAdapter extends FragmentPagerAdapter {

    public List<CurrentPlayerFragment> FRAGMENTS = new ArrayList<>();
    public List<String> TITLES = new ArrayList<>();

    public CurrentPlayerTabsAdapter(FragmentManager fm) {
        super(fm);
    }
    public void add(CurrentPlayerFragment frag, String title)
    {
        this.FRAGMENTS.add(frag);
        this.TITLES.add(title);
    }

    @Override
    public Fragment getItem(int i) {
        return this.FRAGMENTS.get(i);
    }

    @Override
    public int getCount() {
        return this.FRAGMENTS.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.TITLES.get(position);
    }
}
