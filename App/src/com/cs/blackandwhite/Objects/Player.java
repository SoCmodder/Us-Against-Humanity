package com.cs.blackandwhite.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:21 AM
 */
public class Player implements Serializable{
    int id;
    String name;
    Integer score;

    public Player(String name, Integer score, Integer id){
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Player(JSONObject jsonObject) {
		try {
			id = jsonObject.getInt("user_id");
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

    public Integer getScore(){
        return this.score;
    }
}
