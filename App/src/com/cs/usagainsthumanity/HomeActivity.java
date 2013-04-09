package com.cs.usagainsthumanity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.cs.usagainsthumanity.Objects.Game;
import com.savagelook.android.UrlJsonAsyncTask;

public class HomeActivity extends SherlockListActivity {
    //TODO: change this later
    private static final String TASKS_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/?find=in";

    private SharedPreferences mPreferences;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mPreferences.contains("AuthToken")) {
            try {
				loadTasksFromAPI(TASKS_URL);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    private void loadTasksFromAPI(String url) throws UnsupportedEncodingException {
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
