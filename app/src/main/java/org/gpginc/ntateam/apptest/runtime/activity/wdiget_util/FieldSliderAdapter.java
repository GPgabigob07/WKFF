package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.gpginc.ntateam.apptest.R;

public class FieldSliderAdapter extends FragmentPagerAdapter {

    private final Context c;
    public FieldSliderAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.c = c;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos + 1) {
            case 1:
                return FieldSliderFragment.newInstance(c.getString(R.string.title_section1), R.drawable.ruins_bg);
            case 2:
                return FieldSliderFragment.newInstance(c.getString(R.string.title_section2), R.drawable.jungle_bg);
            case 3:
                return FieldSliderFragment.newInstance(c.getString(R.string.title_section3), R.drawable.castle_bg);
            case 4:
                return FieldSliderFragment.newInstance(c.getString(R.string.title_section4), R.drawable.desert_bg);
            default:
                return FieldSliderFragment.newInstance(c.getString(R.string.title_section1), R.drawable.ruins_bg);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}