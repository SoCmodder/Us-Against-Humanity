package com.cs.blackandwhite.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.cs.blackandwhite.Objects.Game;
import com.cs.blackandwhite.R;

import java.util.List;

public class GameArrayAdapter extends BaseAdapter {

    private List<Game> games;
    private LayoutInflater inflator;
    private Context mContext;

    public GameArrayAdapter(Context mContext, List<Game> games){
        this.games = games;
        this.mContext = mContext;
        this.inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return games.size();
    }

    @Override
    public Object getItem(int arg0) {
        return games.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return games.get(arg0).getId();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View v = arg1;
        if(v == null){
            v = inflator.inflate(R.layout.game_list_item, arg2, false);
        }
        Game game = games.get(arg0);
        if(game != null){
            TextView host = (TextView) v.findViewById(R.id.host);
            host.setText("Host: " + game.getHostName());
            TextView slots = (TextView) v.findViewById(R.id.slots);
            slots.setText("Players: " + game.getPlayerList().size() + "/" + game.getSlots());
            TextView ptw = (TextView) v.findViewById(R.id.ptw);
            ptw.setText("Points To Win: " + String.valueOf(game.getPointsToWin()));
            TextView state = (TextView) v.findViewById(R.id.state);
            ImageView alertImg = (ImageView)v.findViewById(R.id.AlertImg);
            TextView alertText = (TextView) v.findViewById(R.id.AlertText);
            if(game.getState() == 1){
                if(game.isCzar() && game.isAllSubmit()){
                    alertImg.setImageResource(R.drawable.all_cards);
                    alertImg.setVisibility(View.VISIBLE);
                    alertText.setText("All Cards Submitted");
                    alertText.setVisibility(View.VISIBLE);
                }else if((!game.isCzar()) && game.isHaveSubmit()){
                    alertImg.setImageResource(R.drawable.card_submitted);
                    alertImg.setVisibility(View.VISIBLE);
                    alertText.setText("You have played");
                    alertText.setVisibility(View.VISIBLE);
                }else if(!game.isCzar()){
                    alertImg.setImageResource(R.drawable.have_submit);
                    alertImg.setVisibility(View.VISIBLE);
                    alertText.setText("You have not played");
                    alertText.setVisibility(View.VISIBLE);

                }else {
                    alertImg.setVisibility(View.INVISIBLE);
                    alertText.setVisibility(View.INVISIBLE);
                }
            }else{
                alertImg.setVisibility(View.INVISIBLE);
                alertText.setVisibility(View.INVISIBLE);
            }

            int gameState = game.getState();
            state.setText(gameState == 0 ? "Game State: Open" : gameState == 1 ? "Game State: In Progress" : "Game State: Ended" );
        }
        //state.setTextColor(gameState == 0? android.R.color.white : gameState == 1? android.R.color.holo_green_light: android.R.color.holo_red_light);
        return v;

    }

}
