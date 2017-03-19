package app.kazucon.connpassclient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.databinding.FragmentFavoriteEventsBinding;

/**
 * Created by kazuumi on 17/03/18.
 */

public class FavoriteEventsFragment extends Fragment {

    private FragmentFavoriteEventsBinding favoriteEventsBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.favoriteEventsBind = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_favorite_events,
                null,
                false
        );

        return this.favoriteEventsBind.getRoot();
    }

}
