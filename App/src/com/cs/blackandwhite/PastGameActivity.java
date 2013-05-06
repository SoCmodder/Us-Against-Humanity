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
import com.cs.blackandwhite.Adapters.GameArrayAdapter;
import com.cs.blackandwhite.Objects.Game;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/29/13
 * Time: 2:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class PastGameActivity extends SherlockListActivity {
    private static final String TASKS_URL = Data.serverUrl + "games/?find=ended";

    private SharedPreferences mPreferences;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setTitle("Past Games");
        setContentView(R.layout.game_list_view);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Game game = (Game) getListView().getItemAtPosition(position);
                Intent intent = new Intent(PastGameActivity.this, ViewGameInfoActivity.class);
                intent.putExtra("game", game);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        loadTasksFromAPI(TASKS_URL);
    }

    private void loadTasksFromAPI(String url){
        GetTasksTask getTasksTask = new GetTasksTask(PastGameActivity.this);
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
                getListView().setAdapter(new GameArrayAdapter(PastGameActivity.this, tasksTitles));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
