package com.cs.usagainsthumanity.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:21 AM
 */
public class Player {
    int id;
    String name;

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }
}
