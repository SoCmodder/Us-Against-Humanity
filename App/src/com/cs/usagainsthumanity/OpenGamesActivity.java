package com.cs.usagainsthumanity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cs.usagainsthumanity.Objects.Game;
import com.savagelook.android.UrlJsonAsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:17 AM
 */
public class OpenGamesActivity extends SherlockListActivity {
    
    private SharedPreferences mPreferences;
    private static final String GAME_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/?find=open";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        getSupportActionBar().setHomeButtonEnabled(true);
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
			Toast.makeText(this, "Needs to be implemented", Toast.LENGTH_SHORT).show();
            //Start a new intent to go to the game creation page.
            Intent gameCreation = new Intent(OpenGamesActivity.this, CreateGameActivity.class);
            startActivity(gameCreation);
			return true;
			
		case R.id.refresh:
			loadOpenGames(GAME_URL);
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
                        Intent derp = new Intent(OpenGamesActivity.this, ViewGameActivity.class);
                        //derp.putExtra("String key", value);
                        startActivity(derp);
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
}