package com.cs.usagainsthumanity.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
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
public class ScoreAdapter extends ArrayAdapter<Player> {

    private List<Player> players;
    private int layoutResourceId;
    private Context mContext;

    public ScoreAdapter(Context mContext, int layoutResourceId, List<Player> players){
        super(mContext, layoutResourceId, players);
        this.players = players;
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public long getItemId(int arg0) {
        return players.get(arg0).getId();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View v = arg1;
        PlayerHolder holder = null;
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(layoutResourceId, arg2, false);


        }
        holder = new PlayerHolder();
        holder.playername = (TextView)v.findViewById(R.id.player_name);
        holder.score = (TextView)v.findViewById(R.id.score);
        holder.playername.setTextColor(Color.BLACK);
        holder.score.setTextColor(Color.BLACK);

        v.setTag(holder);
        Player player = players.get(arg0);
        holder.playername.setText(player.getName());
        if(player.getScore() != null){
            holder.score.setText(player.getScore().toString());
        }
        else{
            holder.score.setText("0");
        }
        //state.setTextColor(gameState == 0? android.R.color.white : gameState == 1? android.R.color.holo_green_light: android.R.color.holo_red_light);
        return v;

    }

    static class PlayerHolder{
        TextView playername;
        TextView score;
    }

}
