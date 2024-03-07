package com.android.arsa.catalogmoviedicoding.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.arsa.catalogmoviedicoding.view.fragment.NowPlayingFragment;
import com.android.arsa.catalogmoviedicoding.view.fragment.UpcomingFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final int pageTab;

    public PagerAdapter(FragmentManager fm, int pageTab) {
        super(fm);
        this.pageTab = pageTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpcomingFragment();
            default:
                return new NowPlayingFragment();
        }
    }

    @Override
    public int getCount() {
        return pageTab;
    }
}
