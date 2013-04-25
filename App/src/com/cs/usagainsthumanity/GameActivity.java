package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.cs.usagainsthumanity.Objects.Player;
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
    ArrayList<String> card_texts;
    ArrayList<Integer> card_ids;
    Bundle bundle;
    Bundle scoreBundle;
    ViewGameFragment viewGameFragment;
    ViewScoreFragment viewScoreFragment;
    String blackCardText;

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
        mPreferences = getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
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
        getTasksTask.execute(url + game_id + "/round");
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
                JSONArray score = data.getJSONArray("score");
                ArrayList<Player> playerList = new ArrayList<Player>();
                blackCardText = data.getJSONObject("black_card").getString("text");
                JSONArray cardTexts = hand.getJSONArray("texts");
                JSONArray ids = hand.getJSONArray("ids");
                if(data.length() > 0){
                    for (int i = 0; i < cardTexts.length(); i++) {
                        card_texts.add(cardTexts.getString(i));
                        card_ids.add(ids.getInt(i));
                    }
                    for(int i=0; i<score.length(); i++){
                        playerList.add(new Player(score.getJSONObject(i)));
                    }
                    scoreBundle.putSerializable("players", playerList);
                    bundle.putStringArrayList("card_texts", card_texts);
                    bundle.putIntegerArrayList("card_ids", card_ids);
                    bundle.putString("black_card", blackCardText);
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
