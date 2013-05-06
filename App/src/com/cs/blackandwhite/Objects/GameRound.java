package com.cs.blackandwhite.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameRound implements Serializable{
    private String blacktext;
    private String winninguser;
    private int id;
    List<Submitted> submittedList;

    public GameRound(JSONObject jsonObject){
        try{
            blacktext = jsonObject.getString("black_card");
            winninguser = jsonObject.getString("winninguser");
            id = jsonObject.getInt("id");
            JSONArray array = jsonObject.getJSONArray("submitted");
            submittedList = new ArrayList<Submitted>(array.length());
            for(int i = 0; i < array.length(); i++){
                submittedList.add(new Submitted(array.getJSONObject(i), id));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public List<Submitted> getSubmittedList() {
        return submittedList;
    }

    public void setSubmittedList(List<Submitted> submittedList) {
        this.submittedList = submittedList;
    }

    public String getBlacktext() {
        return blacktext;
    }

    public void setBlacktext(String blacktext) {
        this.blacktext = blacktext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWinninguser() {
        return winninguser;
    }

    public void setWinninguser(String winninguser) {
        this.winninguser = winninguser;
    }

}
