package com.cs.blackandwhite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder
 * Date: 5/6/13
 * Time: 12:44 AM
 */
public class ViewTutorialActivity extends Activity {
    Integer counter;
    TextView text;
    ImageView image;
    Button dismiss;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tutorial);
        String whichTutorial = getIntent().getStringExtra("tutorial");

        counter = 0;
        dismiss = (Button)findViewById(R.id.dismissal);
        image = (ImageView)findViewById(R.id.tutorial_image);
        text = (TextView)findViewById(R.id.tutorial_textview);

        if(whichTutorial.equalsIgnoreCase("gameplay")){
            dismiss.setText("Next");
            dismiss.setMinimumWidth(30);
            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter+=1;
                    switch(counter){
                        case 1:
                            text.setText("PRESS THIS ICON FOR GAME INFORMATION");
                            image.setImageResource(R.drawable.ic_action_info);
                            break;
                        case 2:
                            image.setImageResource(R.drawable.ic_action_refresh);
                            text.setText("PRESS THIS ONE TO REFRESH THE GAME");
                            break;
                        case 3:
                            dismiss.setText("Got it!");
                            image.setImageResource(R.drawable.icon);
                            text.setText("FINALLY, YOU CAN GO TO THE SETTINGS IF YOU WISH TO GET NOTIFICATIONS FROM THE GAME");
                            break;
                        case 4:
                            finish();
                            break;
                    }
                }
            });
        }
        else if(whichTutorial.equalsIgnoreCase("create")){
            dismiss.setText("Next");
            dismiss.setMinimumWidth(30);
            text.setText("PRESS THIS ICON TO VIEW OPEN GAMES OR CREATE A NEW ONE");
            image.setImageResource(R.drawable.ic_action_find);
            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter+=1;
                    switch(counter){
                        case 1:
                            image.setImageResource(R.drawable.ic_action_profile);
                            text.setText("TAP THIS ICON TO VIEW YOUR PROFILE");
                            break;
                        case 2:
                            image.setImageResource(R.drawable.ic_action_past);
                            text.setText("TAP THIS TO SEE YOUR PAST GAMES");
                            break;
                        case 3:
                            image.setImageResource(R.drawable.ic_action_refresh);
                            text.setText("TAP THIS TO REFRESH THE GAMES YOU ARE IN");
                            break;
                        case 4:
                            finish();
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
