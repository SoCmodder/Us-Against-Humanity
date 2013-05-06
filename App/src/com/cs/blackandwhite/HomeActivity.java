package com.cs.blackandwhite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cs.blackandwhite.Adapters.GameArrayAdapter;
import com.cs.blackandwhite.Objects.Game;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends SherlockListActivity {
    private static final String TASKS_URL = Data.serverUrl + "games/?find=in";

    private SharedPreferences mPreferences;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setTitle("My Games");
        setContentView(R.layout.game_list_view);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = (Game) getListView().getItemAtPosition(position);
                if(game.getState() == 1){
                    Intent derp = new Intent(HomeActivity.this, GameActivity.class);
                    derp.putExtra("gameID", game.getId());
                    startActivity(derp);
                }else{
                    Intent intent = new Intent(HomeActivity.this, ViewGameInfoActivity.class);
                    intent.putExtra("game", game);
                    startActivity(intent);
                }

            }
        });
        //showTutorial();
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
			startActivityForResult(new Intent(HomeActivity.this, UserInfoActivity.class),0);
			return true;
			
		case R.id.find:
			Intent intent = new Intent(HomeActivity.this, OpenGamesActivity.class);
            startActivityForResult(intent, 0);
            return true;
		case R.id.refresh:
			 loadTasksFromAPI(TASKS_URL);
			 return true;
		case R.id.settings:
			Intent sIntent = new Intent(HomeActivity.this, SettingsActivity.class);
			startActivityForResult(sIntent, 0);
			return true;
        case R.id.past:
            Intent mIntent = new Intent(HomeActivity.this, PastGameActivity.class);
            startActivity(mIntent);
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
        stopService(new Intent(this, NotificationService.class));
        if(mPreferences.getBoolean("notifications", false)){
            startService(new Intent(this, NotificationService.class));
        }else {
            stopService(new Intent(this, NotificationService.class));
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
            showTutorial();
        }
    }

    public void showTutorial(){
        if(!hasRun()){
            Intent tutorial = new Intent(HomeActivity.this, ViewTutorialActivity.class);
            tutorial.putExtra("tutorial", "create");
            startActivity(tutorial);
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        //showTutorial();
    }

    private boolean hasRun(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean ranBefore = preferences.getBoolean("OpenGames", false);
        if(!ranBefore){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("OpenGames", true);
            editor.commit();
        }
        return ranBefore;
    }
}
