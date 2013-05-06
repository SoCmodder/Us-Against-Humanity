package com.cs.blackandwhite.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/26/13
 * Time: 2:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardObj {

    private String text;
    private int id;

    public CardObj(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
