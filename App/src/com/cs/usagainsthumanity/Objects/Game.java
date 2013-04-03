package com.cs.usagainsthumanity.Objects;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:21 AM
 */
public class Game {
    int id;
    int pointsToWin;
    int slots;
    boolean isPrivate;
    List<Player> playerList;

    public Game(int id, int pointsToWin, int slots, boolean isPrivate, List<Player> playerList){
        this.id = id;
        this.playerList = playerList;
        this.pointsToWin = pointsToWin;
        this.slots = slots;
        this.isPrivate = isPrivate;
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
        return this.getSlots();
    }

    public boolean getPrivacy(){
        return this.isPrivate;
    }
}
