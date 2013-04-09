package com.cs.usagainsthumanity.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:21 AM
 */
public class Player {
    int id;
    String name;
    int score;

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Player(JSONObject jsonObject) {
		try {
			id = jsonObject.getInt("id");
			name = jsonObject.getString("name");
			score = jsonObject.getInt("score");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }
}
