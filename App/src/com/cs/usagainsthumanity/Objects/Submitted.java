package com.cs.usagainsthumanity.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 9:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class Submitted implements Serializable{

    private Integer gameuserId;
    private int gameround_id;
    private String username;
    private List<String> Submitted = new ArrayList<String>();

    public Submitted(JSONObject jsonObject, int gameround_id){
        this.gameround_id = gameround_id;
        try {
            gameuserId = jsonObject.getInt("gameuser_id");
            JSONArray jsonArray = jsonObject.getJSONArray("whitetext");
            int length = jsonArray.length();
            for(int i = 0; i < length; i++){
                Submitted.add(jsonArray.getString(i));
            }
            this.username = jsonObject.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public Integer getGameuserId() {
        return gameuserId;
    }

    public void setGameuserId(Integer gameuserId) {
        this.gameuserId = gameuserId;
    }

    public List<String> getSubmitted() {
        return Submitted;
    }

    public void setSubmitted(List<String> submitted) {
        Submitted = submitted;
    }

    public int getGameround_id() {
        return gameround_id;
    }

    public void setGameround_id(int gameround_id) {
        this.gameround_id = gameround_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if(!(obj instanceof Submitted)){
            return false;
        }

        Submitted rhs = (Submitted) obj;
        if(gameuserId == rhs.getGameuserId()){
            if(Submitted.equals(rhs.getSubmitted())){
                return true;
            }
        }
        return false;
    }

}
