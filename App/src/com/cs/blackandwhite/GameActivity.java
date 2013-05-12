package com.cs.blackandwhite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cs.blackandwhite.Objects.Game;
import com.cs.blackandwhite.Objects.Player;
import com.cs.blackandwhite.Objects.Submitted;
import com.savagelook.android.UrlJsonAsyncTask;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 2:51 PM
 */
public class GameActivity extends SlidingFragmentActivity {
    SharedPreferences mPreferences;
    private static final String HAND_URL = Data.serverUrl + "games/";
    private int game_id = -1;
    private Game gameObj = null;
    ArrayList<String> card_texts;
    ArrayList<Integer> card_ids;
    Bundle bundle;
    Bundle scoreBundle;
    ViewGameFragment viewGameFragment;
    ViewScoreFragment viewScoreFragment;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getIntent().getIntExtra("created", 0) == 1){
            Intent intent = new Intent(GameActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setMode(SlidingMenu.LEFT);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        card_texts = new ArrayList<String>();
        card_ids = new ArrayList<Integer>();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        game_id = getIntent().getIntExtra("gameID", -1);
        bundle = new Bundle();
        scoreBundle = new Bundle();

        loadRound(HAND_URL);

        bundle.putInt("gameID", game_id);
        scoreBundle.putInt("gameID", game_id);

        viewGameFragment = new ViewGameFragment();
        viewScoreFragment = new ViewScoreFragment();


        setContentView(R.layout.menu_frame);
        setBehindContentView(R.layout.menu_frame2);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        showTutorial();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    private void loadRound(String url) {
        GetTasksTask getTasksTask = new GetTasksTask(GameActivity.this);
        getTasksTask.setMessageLoading("Loading Hand...");
        getTasksTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        String apiURL = url + game_id + "/round";
        getTasksTask.execute(apiURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity_game, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.info:
                Intent intent = new Intent(GameActivity.this, ViewGameInfoActivity.class);
                intent.putExtra("game", gameObj);
                startActivity(intent);

                return true;
            case R.id.refresh:
                Intent derp = new Intent(GameActivity.this, GameActivity.class);
                derp.putExtra("gameID", gameObj.getId());
                startActivity(derp);
                finish();
                return true;
            case R.id.settings:
                Intent sIntent = new Intent(GameActivity.this, SettingsActivity.class);
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
                JSONObject game = data.getJSONObject("game");
                JSONObject innergame = game.getJSONObject("game");

                JSONObject hand = data.getJSONObject("hand");
                JSONObject blackCard = data.getJSONObject("black_card");
                JSONArray score = data.getJSONArray("score");
                ArrayList<Player> playerList = new ArrayList<Player>();
                JSONArray cardTexts = hand.getJSONArray("texts");
                JSONArray submitted = data.getJSONArray("submitted");
                JSONArray ids = hand.getJSONArray("ids");
                if(data.length() > 0){
                    for (int i = 0; i < cardTexts.length(); i++) {
                        card_texts.add(cardTexts.getString(i));
                        card_ids.add(ids.getInt(i));
                    }
                    ArrayList<Submitted> submittedArrayList = new ArrayList<Submitted>();
                    for(int i = 0; i < submitted.length(); i++){
                        submittedArrayList.add(new Submitted(submitted.getJSONObject(i),1));
                    }
                    for(int i=0; i<score.length(); i++){
                        Player p = new Player(score.getJSONObject(i));
                        playerList.add(p);
                    }
                    gameObj = new Game(game);
                    scoreBundle.putSerializable("players", playerList);
                    bundle.putStringArrayList("card_texts", card_texts);
                    bundle.putIntegerArrayList("card_ids", card_ids);
                    bundle.putString("blackCardText", blackCard.getString("text"));
                    bundle.putInt("blackCardNum", blackCard.getInt("numwhite"));
                    bundle.putBoolean("is_czar", data.getBoolean("is_czar"));
                    bundle.putSerializable("submitted", submittedArrayList);
                    viewGameFragment.setArguments(bundle);
                    viewScoreFragment.setArguments(scoreBundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, viewGameFragment).commit();
                    getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame2, viewScoreFragment).commit();
                    if(!hasRunToggle()){
                        toggle();
                    }
                    if(innergame.getInt("state") == 2 || innergame.getInt("state") == 0){
                        Intent intent = new Intent(GameActivity.this, ViewGameInfoActivity.class);
                        intent.putExtra("game", gameObj);
                        startActivity(intent);
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                super.onPostExecute(json);
            }
        }
    }


    private class RemoveUserTask extends UrlJsonAsyncTask {
        public RemoveUserTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpDelete delete = new HttpDelete(urls[0]);
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    JSONArray holder = new JSONArray();
                    // setup the returned values in case
                    // something goes wrong
                    json.put("success", false);
                    json.put("info", "Something went wrong. Retry!");
                    // add the user email and password to
                    // the params
                    // setup the request headers
                    delete.setHeader("Accept", "application/json");
                    delete.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(delete, responseHandler);
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
                    Toast.makeText(GameActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (Exception e) {
                // something went wrong: show a Toast
                // with the exception message
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }

    public void showTutorial(){
        if(!hasRun()){
            Intent tutorial = new Intent(GameActivity.this, ViewTutorialActivity.class);
            tutorial.putExtra("tutorial", "gameplay");
            startActivity(tutorial);
            onResume();
        }
    }

    private boolean hasRun(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        int time = preferences.getInt("time", 0);
        if(!ranBefore && time <=1){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return ranBefore;
    }

    private boolean hasRunToggle(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean ranBefore = preferences.getBoolean("toggle", false);
        if(!ranBefore){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("toggle", true);
            editor.commit();
        }
        return ranBefore;
    }
}
