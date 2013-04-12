package com.cs.usagainsthumanity;

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

public class HomeActivity extends SherlockListActivity {
    private static final String TASKS_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/?find=in";

    private SharedPreferences mPreferences;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        setContentView(R.layout.game_list_view);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = (Game) getListView().getItemAtPosition(position);
                if(game.getState() == 1){
                    Intent derp = new Intent(HomeActivity.this, ViewGameActivity.class);
                    derp.putExtra("game_id", game.getId());
                    startActivity(derp);
                }else{
                    Intent intent = new Intent(HomeActivity.this, ViewGameInfoActivity.class);
                    intent.putExtra("game", game);
                    startActivity(intent);
                }

            }
        });
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_home, menu);
		return true;
	}

	public boolean onPrepareOptionsMenu(Menu menu){
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {

		case R.id.profile:
			Toast.makeText(this, "Needs to be implemented", Toast.LENGTH_SHORT).show();
			return true;
			
		case R.id.find:
			Intent intent = new Intent(HomeActivity.this, OpenGamesActivity.class);
            startActivityForResult(intent, 0);
            return true;
		case R.id.refresh:
			 loadTasksFromAPI(TASKS_URL);
			 return true;
		default:
			return false;

		}
	}

    @Override
    public void onResume() {
        super.onResume();

        if (mPreferences.contains("AuthToken")) {
            loadTasksFromAPI(TASKS_URL);
        } else {
            Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    private void loadTasksFromAPI(String url){
        GetTasksTask getTasksTask = new GetTasksTask(HomeActivity.this);
        getTasksTask.setMessageLoading("Loading games...");
        getTasksTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getTasksTask.execute(url);
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
                getListView().setAdapter(new GameArrayAdapter(HomeActivity.this, tasksTitles));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
