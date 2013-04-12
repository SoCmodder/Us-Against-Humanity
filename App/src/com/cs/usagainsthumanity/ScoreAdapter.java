package com.cs.usagainsthumanity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cs.usagainsthumanity.Objects.Game;
import com.cs.usagainsthumanity.Objects.Player;
import com.cs.usagainsthumanity.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScoreAdapter  extends BaseAdapter {

    private List<Player> players;
    private LayoutInflater inflator;
    private Context mContext;

    public ScoreAdapter(Context mContext, List<Player> players){
        this.players = players;
        this.mContext = mContext;
        this.inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int arg0) {
        return players.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return players.get(arg0).getId();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View v = arg1;
        if(v == null){
            v = inflator.inflate(R.layout.score_list_item, arg2, false);
        }
        Player player = players.get(arg0);
        TextView playername = (TextView) v.findViewById(R.id.player_name);
        TextView score = (TextView) v.findViewById(R.id.score);
        playername.setText(player.getName());
        score.setText(player.getScore().toString());
        //state.setTextColor(gameState == 0? android.R.color.white : gameState == 1? android.R.color.holo_green_light: android.R.color.holo_red_light);
        return v;

    }

}
