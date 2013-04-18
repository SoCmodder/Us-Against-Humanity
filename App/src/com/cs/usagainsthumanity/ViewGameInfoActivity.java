package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cs.usagainsthumanity.Adapters.GameArrayAdapter;
import com.cs.usagainsthumanity.Adapters.GameRoundAdapter;
import com.cs.usagainsthumanity.Adapters.ScoreAdapter;
import com.cs.usagainsthumanity.Objects.Game;
import com.cs.usagainsthumanity.Objects.GameRound;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/10/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class ViewGameInfoActivity extends SherlockFragmentActivity {

    private SharedPreferences mPreferences;
    private ListView lastRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        setContentView(R.layout.activity_view_game_info);
        Game game = (Game) getIntent().getSerializableExtra("game");
        TextView hostname = (TextView) findViewById(R.id.host_name);
        hostname.setText(game.getHostName());

        ScoreAdapter mAdapter = new ScoreAdapter(ViewGameInfoActivity.this, game.getPlayerList());
        ListView lv = (ListView) findViewById(R.id.playerlist);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        lastRound = (ListView) findViewById(R.id.lastroundlist);
        loadLastRoundFromAPI(Data.serverUrl + "games/" +  game.getId() + "/last_round" );

    }

    private void loadLastRoundFromAPI(String url){
        GetTasksTask getTasksTask = new GetTasksTask(ViewGameInfoActivity.this);
        getTasksTask.setMessageLoading("Loading");
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
                List<GameRound> gameRounds = new ArrayList<GameRound>();
                gameRounds.add(new GameRound(data));
                lastRound.setAdapter(new GameRoundAdapter(ViewGameInfoActivity.this, gameRounds));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
