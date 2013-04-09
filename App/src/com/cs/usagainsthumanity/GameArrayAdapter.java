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
		TextView host = (TextView) v.findViewById(R.id.hostname);
		host.setText(game.getHostName());
		int openSlots = game.getSlots() - game.getPlayerList().size();
		TextView slots = (TextView) v.findViewById(R.id.slots);
		slots.setText(openSlots + "/" + game.getSlots());
			
		return v;

	}

}
