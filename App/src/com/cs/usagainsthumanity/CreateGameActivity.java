package com.cs.usagainsthumanity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.cs.usagainsthumanity.Objects.Player;

import java.util.List;

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
    Button submit;
    EditText winningPts, maximumPlayers;
    CheckBox privacy;

    int ptsToWin = 0, maxPlayers = 0;
    Boolean isPrivate = false;
    String hostname = null;
    List<Player> playerList;
    int state;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_create);

        submit = (Button) findViewById(R.id.submit_new_game_button);
        winningPts = (EditText) findViewById(R.id.pts_to_win);
        maximumPlayers = (EditText) findViewById(R.id.max_players);
        privacy = (CheckBox) findViewById(R.id.privacy_checkbox);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: needs to be implemented
            }
        });

    }
}