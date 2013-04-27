package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.cs.usagainsthumanity.Objects.Game;
import com.cs.usagainsthumanity.Objects.Player;
import com.cs.usagainsthumanity.Objects.Submitted;
import com.savagelook.android.UrlJsonAsyncTask;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 2:51 PM
 */
public class GameActivity extends SlidingFragmentActivity {
    SharedPreferences mPreferences;
    private static final String HAND_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/";
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
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
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

        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        setContentView(R.layout.menu_frame);
        setBehindContentView(R.layout.menu_frame2);


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
                        submittedArrayList.add(new Submitted(submitted.getJSONObject(i)));
                    }
                    for(int i=0; i<score.length(); i++){
                        Player p = new Player(score.getJSONObject(i).getString("name"),
                                score.getJSONObject(i).getInt("score"));
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
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }


}
