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
    private List<String> Submitted;

    public Submitted(JSONObject jsonObject){
        try {
            gameuserId = jsonObject.getInt("gameuser_id");
            JSONArray jsonArray = jsonObject.getJSONArray("whitetext");
            int length = jsonArray.length();
            Submitted = new ArrayList<String>(length);
            for(int i = 0; i < length; i++){
                Submitted.add(jsonArray.getString(i));
            }
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

}
