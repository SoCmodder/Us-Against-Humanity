package com.cs.usagainsthumanity;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs.usagainsthumanity.Objects.Game;

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
		TextView host = (TextView) v.findViewById(R.id.host);
		host.setText(game.getHostName());
		int openSlots = game.getSlots() - game.getPlayerList().size();
		TextView slots = (TextView) v.findViewById(R.id.slots);
		slots.setText(openSlots + "/" + game.getSlots());
		TextView ptw = (TextView) v.findViewById(R.id.ptw);
		ptw.setText(String.valueOf(game.getPointsToWin()));
		TextView state = (TextView) v.findViewById(R.id.state);
		int gameState = game.getState();
		state.setText(gameState == 0 ? "Open" : gameState == 1 ? "In Progress" : "Ended" );
		//state.setTextColor(gameState == 0? android.R.color.white : gameState == 1? android.R.color.holo_green_light: android.R.color.holo_red_light); 
		return v;

	}

}
