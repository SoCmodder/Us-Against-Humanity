package com.cs.usagainsthumanity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cs.usagainsthumanity.Adapters.GameArrayAdapter;
import com.cs.usagainsthumanity.Objects.Game;
import com.savagelook.android.UrlJsonAsyncTask;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:17 AM
 */
public class OpenGamesActivity extends SherlockListActivity {
    
    private SharedPreferences mPreferences;
    private static final String GAME_URL = Data.serverUrl + "games/?find=open";
    private static final String CREATE_GAME_URL = Data.serverUrl + "games/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Open Games");
        setContentView(R.layout.game_list_view);
        loadOpenGames(GAME_URL);
    }
    
    private void loadOpenGames(String url) {
        GetTasksTask getTasksTask = new GetTasksTask(OpenGamesActivity.this);
        getTasksTask.setMessageLoading("Loading games...");
        getTasksTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getTasksTask.execute(url);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_open_games, menu);
		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu){
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		
		case android.R.id.home:
			finish();
			return true;
		case R.id.create:
            //Start a new intent to go to the game creation page.
            Intent gameCreation = new Intent(OpenGamesActivity.this, CreateGameActivity.class);
            startActivity(gameCreation);
			return true;
			
		case R.id.refresh:
			loadOpenGames(GAME_URL);
			return true;
		case R.id.settings:
			Intent sIntent = new Intent(OpenGamesActivity.this, SettingsActivity.class);
			startActivityForResult(sIntent, 0);
			return true;
		default:
			return false;

		}
	}

    private class GetTasksTask extends UrlJsonAsyncTask {
        public GetTasksTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
            	JSONObject data = json.getJSONObject("data");
                JSONArray jsonTasks = data.getJSONArray("games");
                int length = jsonTasks.length();
                List<Game> tasksTitles = new ArrayList<Game>(length);

                for (int i = 0; i < length; i++) {
                    tasksTitles.add(new Game(jsonTasks.getJSONObject(i)));
                }

                getListView().setAdapter(new GameArrayAdapter(OpenGamesActivity.this, tasksTitles));
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Do some stuff when an item is clicked
                        //get info about the selected item
                        //pass that info to the next activity
                        //profit
                        final Game game = (Game) getListView().getItemAtPosition(position);
                        TextView textView = new TextView(OpenGamesActivity.this);
                        textView.setText("Would you like to join " + game.getHostName() + "\'s game?");
                        AlertDialog alertDialog = new AlertDialog.Builder(OpenGamesActivity.this)
                                .setTitle("Join Game")
                                .setView(textView)
                                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        CreateGameTask createGameTask = new CreateGameTask(OpenGamesActivity.this);
                                        createGameTask.setMessageLoading("Joining Game...");
                                        createGameTask.setAuthToken(mPreferences.getString("AuthToken", ""));
                                        createGameTask.execute(CREATE_GAME_URL + game.getId());
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create();
                        alertDialog.show();

                    }
                });
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }


    private class CreateGameTask extends UrlJsonAsyncTask {
        public CreateGameTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            super.doInBackground(urls);
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(urls[0]);
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

                    // setup the request headers
                    put.setHeader("Accept", "application/json");
                    put.setHeader("Authorization", "Token token=\"" + this.getAuthToken() + "\"");
                    put.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(put, responseHandler);
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
                    int gameId = json.getJSONObject("data").getJSONObject("gameuser").getInt("game_id");
                    // launch the HomeActivity and close this one
                    Intent intent = new Intent(OpenGamesActivity.this, GameActivity.class);
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