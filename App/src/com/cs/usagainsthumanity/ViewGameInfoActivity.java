package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cs.usagainsthumanity.Adapters.GameRoundAdapter;
import com.cs.usagainsthumanity.Adapters.ScoreAdapter;
import com.cs.usagainsthumanity.Objects.Game;
import com.cs.usagainsthumanity.Objects.GameRound;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersListView;
import com.savagelook.android.UrlJsonAsyncTask;
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
    private boolean mTwoPane = false;
    private Game game;
    private StickyListHeadersListView stickyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_view_game_info);
        if(findViewById(R.id.history_list) != null){
            mTwoPane = true;
        }
        game = (Game) getIntent().getSerializableExtra("game");
        if(game == null){
            //TODO load information below asynronously
        }else{
            TextView hostname = (TextView) findViewById(R.id.host_name);
            hostname.setText(game.getHostName());

            ScoreAdapter mAdapter = new ScoreAdapter(ViewGameInfoActivity.this, R.layout.score_list_item, game.getPlayerList());
            ListView lv = (ListView) findViewById(R.id.playerlist);
            lv.setAdapter(mAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            stickyList = (StickyListHeadersListView) findViewById(R.id.lastroundlist);

            if(!mTwoPane){
                loadLastRoundFromAPI(Data.serverUrl + "games/" +  game.getId() + "/last_round" );
            }else{
                Bundle arguments = new Bundle();
                arguments.putInt(ViewGameHistoryFragment.ARG_ITEM_ID, game.getId());
                ViewGameHistoryFragment fragment = new ViewGameHistoryFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.history_list, fragment)
                        .commit();

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!mTwoPane){
            menu.add(Menu.NONE, 1337, 20, "Game History");
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case 1337:
                Intent i = new Intent(this, ViewGameHistoryActivity.class);
                i.putExtra(ViewGameHistoryFragment.ARG_ITEM_ID, game.getId());
                startActivity(i);
                return true;
            default:
                return false;
        }
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

                //TODO Everything after this line needs to be made into a function
            stickyList.setAdapter(new GameRoundAdapter(ViewGameInfoActivity.this, R.layout.submitted_game_item, gameRounds));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
