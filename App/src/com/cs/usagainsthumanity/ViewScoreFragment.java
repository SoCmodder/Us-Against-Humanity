package com.cs.usagainsthumanity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockListFragment;
import com.cs.usagainsthumanity.Adapters.ScoreAdapter;
import com.cs.usagainsthumanity.Objects.Player;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewScoreFragment extends SherlockListFragment {
    ArrayList<Player> playerList;
    SharedPreferences mPreferences;
    private int game_id = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        playerList = (ArrayList<Player>) getArguments().getSerializable("players");
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getSherlockActivity());

        setListAdapter(new ScoreAdapter(getSherlockActivity().getApplicationContext(), R.layout.score_list_item, playerList));

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
