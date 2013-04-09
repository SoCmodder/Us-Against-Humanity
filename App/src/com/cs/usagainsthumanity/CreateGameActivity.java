package com.cs.usagainsthumanity;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder
 * Date: 4/9/13
 * Time: 2:34 PM
 * Description: Form for creating a new game
 */

/*  Game stuff:
    int id;
    int pointsToWin;
    int slots;
    boolean isPrivate;
    String hostname;
    List<Player> playerList;
    int state = 0;
 */

public class CreateGameActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_create);

    }
}