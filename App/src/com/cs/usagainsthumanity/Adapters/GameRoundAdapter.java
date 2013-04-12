package com.cs.usagainsthumanity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cs.usagainsthumanity.Objects.GameRound;
import com.cs.usagainsthumanity.Objects.Player;
import com.cs.usagainsthumanity.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameRoundAdapter extends BaseAdapter{
    private List<GameRound> rounds;
    private LayoutInflater inflator;
    private Context mContext;

    public GameRoundAdapter(Context mContext, List<GameRound> rounds){
        this.rounds = rounds;
        this.mContext = mContext;
        this.inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return rounds.size();
    }

    @Override
    public Object getItem(int arg0) {
        return rounds.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return rounds.get(arg0).hashCode();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View v = arg1;
        if(v == null){
            v = inflator.inflate(R.layout.game_round_item, arg2, false);
        }
        GameRound
        Player player = players.get(arg0);
        TextView playername = (TextView) v.findViewById(R.id.player_name);
        TextView score = (TextView) v.findViewById(R.id.score);
        playername.setText(player.getName());
        score.setText(player.getScore().toString());
        //state.setTextColor(gameState == 0? android.R.color.white : gameState == 1? android.R.color.holo_green_light: android.R.color.holo_red_light);
        return v;

    }
}
