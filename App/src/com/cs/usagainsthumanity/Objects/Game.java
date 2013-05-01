package com.cs.usagainsthumanity.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:21 AM
 */
public class Game implements Serializable{
    int id;
    int pointsToWin;
    int slots;
    boolean isPrivate;
    boolean isCzar;
    boolean allSubmit;
    boolean haveSubmit;
    String hostname;
    List<Player> playerList;
    int state = 0;

    public Game(int id, int pointsToWin, int slots, boolean isPrivate, String hostname, int state, List<Player> playerList){
        this.id = id;
        this.playerList = playerList;
        this.pointsToWin = pointsToWin;
        this.slots = slots;
        this.hostname = hostname;
        this.isPrivate = isPrivate;
        this.state = state;
    }

    public Game(JSONObject jsonObject) {
		try {
			hostname = jsonObject.getString("host");
			JSONObject game = jsonObject.getJSONObject("game");
			id = game.getInt("id");
			pointsToWin = game.getInt("points_to_win");
			slots = game.getInt("slots");
			isPrivate = false;
			state = game.getInt("state");
            allSubmit = jsonObject.getBoolean("all_submit");
            haveSubmit = jsonObject.getBoolean("havesubmit");
            isCzar = jsonObject.getBoolean("is_czar");
			JSONArray players = jsonObject.getJSONArray("players");
			int length = players.length();
			playerList = new ArrayList<Player>(length);

            for (int i = 0; i < length; i++) {
                playerList.add(new Player(players.getJSONObject(i)));
            }
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}

	public int getId(){
        return this.id;
    }

    public List<Player> getPlayerList(){
        return this.playerList;
    }

    public int getPointsToWin(){
        return this.pointsToWin;
    }

    public int getSlots(){
        return this.slots;
    }
    
    public String getHostName(){
    	return this.hostname;
    }

    public boolean getPrivacy(){
        return this.isPrivate;
    }

	public int getState() {
		return state;
	}

    public boolean isCzar() {
        return isCzar;
    }

    public void setCzar(boolean czar) {
        isCzar = czar;
    }

    public boolean isAllSubmit() {
        return allSubmit;
    }

    public void setAllSubmit(boolean allSubmit) {
        this.allSubmit = allSubmit;
    }

    public boolean isHaveSubmit() {
        return haveSubmit;
    }

    public void setHaveSubmit(boolean haveSubmit) {
        this.haveSubmit = haveSubmit;
    }
}
