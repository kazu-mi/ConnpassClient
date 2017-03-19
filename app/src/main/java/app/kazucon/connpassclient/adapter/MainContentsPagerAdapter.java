package app.kazucon.connpassclient.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.fragment.FavoriteEventsFragment;
import app.kazucon.connpassclient.fragment.JoinEventsFragment;
import app.kazucon.connpassclient.fragment.NewEventsFragment;
import app.kazucon.connpassclient.fragment.SoonEventsFragment;

/**
 * Created by kazuumi on 17/03/18.
 */

public class MainContentsPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments = {
            new NewEventsFragment(),
            new SoonEventsFragment(),
            new FavoriteEventsFragment(),
            new JoinEventsFragment(),
    };
    public int[] iconId = {
            R.drawable.ic_new,
            R.drawable.ic_soon,
            R.drawable.ic_favorite,
            R.drawable.ic_mine,
    };
    public int[] titleId = {
            R.string.events_new,
            R.string.events_soon,
            R.string.events_favorite,
            R.string.events_join,
    };

    public MainContentsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments[position];
    }

    @Override
    public long getItemId(int position) {
        return fragments[position].hashCode();
    }

}
