package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.cs.usagainsthumanity.Adapters.ScoreAdapter;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.cs.usagainsthumanity.Objects.Player;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewScoreFragment extends SherlockListFragment {
    CardUI cardView;
    CardStack cardStack;
    CardUI blackCardView;
    ArrayList<Player> playerList;
    SharedPreferences mPreferences;
    private int game_id = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View v = inflater.inflate(R.layout.activity_view_score, container, false);
        playerList = (ArrayList<Player>) getArguments().getSerializable("players");


        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, playerList);

        //setListAdapter(adapter);

        //this.setListAdapter(new ScoreAdapter(getSherlockActivity().getApplicationContext(), playerList));

        setListAdapter(new ScoreAdapter(getSherlockActivity().getApplicationContext(), R.layout.score_list_item, playerList));


        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
