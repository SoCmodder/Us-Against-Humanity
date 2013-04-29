package com.cs.usagainsthumanity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.cs.usagainsthumanity.Objects.Player;
import com.savagelook.android.UrlJsonAsyncTask;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

public class CreateGameActivity extends SherlockActivity {
    Button submit;
    EditText winningPts, maximumPlayers;
    CheckBox privacy;
    private static final String CREATE_GAME_URL = Data.serverUrl + "games";
    SharedPreferences mPreferences;

    Integer ptsToWin = 0, maxPlayers = 0;
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
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGame();
            }
        });

    }

    public void createGame(){
        ptsToWin = Integer.valueOf(winningPts.getText().toString());
        maxPlayers = Integer.valueOf(maximumPlayers.getText().toString());
        isPrivate = privacy.isChecked();
        if(ptsToWin<1 || ptsToWin==null){
            winningPts.setError("Please enter a value of 1 or more.");
        }else if(maxPlayers<3 || maxPlayers==null){
            maximumPlayers.setError("Minimum of 3 players");
        }
        else{
            CreateGameTask createGameTask = new CreateGameTask(CreateGameActivity.this);
            createGameTask.setMessageLoading("Creating Game...");
            //createGameTask.setAuthToken(mPreferences.getString("AuthToken", ""));
            createGameTask.execute(CREATE_GAME_URL + "?auth_token=" + mPreferences.getString("AuthToken", ""));
        }

    }

    private class CreateGameTask extends UrlJsonAsyncTask {
        public CreateGameTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject gameObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    // setup the returned values in case
                    // something goes wrong
                    json.put("success", false);
                    json.put("info", "Something went wrong. Retry!");
                    // add the user email and password to
                    // the params
                    gameObj.put("points_to_win", ptsToWin);
                    gameObj.put("slots", maxPlayers);
                    gameObj.put("private", isPrivate);

                    holder.put("game", gameObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);

                    // setup the request headers
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
                    json.put("info", "There was an error creating the game. Please retry!");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("IO", "" + e);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSON", "" + e);
            }

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.getBoolean("success")) {
                    // everything is ok
                    int gameId = json.getJSONObject("data").getJSONObject("game").getInt("id");

                    // launch the HomeActivity and close this one
                    Intent intent = new Intent(CreateGameActivity.this, GameActivity.class);
                    intent.putExtra("gameID", gameId);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}