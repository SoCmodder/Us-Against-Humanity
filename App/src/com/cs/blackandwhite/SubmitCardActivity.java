package com.cs.blackandwhite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Development
 * Date: 5/10/13
 * Time: 7:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubmitCardActivity extends SherlockActivity {
    EditText white, black;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_new_cards);

        white = (EditText)findViewById(R.id.white_card_et);
        black = (EditText)findViewById(R.id.black_card_et);
        Button submit = (Button)findViewById(R.id.submit_new_card_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(white.getText() != null && black.getText() != null){
                    // submit both black and white
                }
                else if(white.getText() != null){
                    // submit only white
                }
                else if(black.getText() != null){
                    // submit only black
                }
                else{
                    white.setError("Please enter something into one or both of the fields.");
                }
            }
        });
    }
}