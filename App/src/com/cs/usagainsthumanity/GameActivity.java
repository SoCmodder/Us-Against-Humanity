package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.cs.usagainsthumanity.Objects.CustomCard;
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

        loadHand(HAND_URL);

        bundle.putInt("gameID", game_id);
        scoreBundle.putInt("gameID", game_id);

        viewGameFragment = new ViewGameFragment();
        viewGameFragment.setArguments(bundle);

        viewScoreFragment = new ViewScoreFragment();
        viewScoreFragment.setArguments(scoreBundle);

        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        setContentView(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, viewGameFragment).commit();
        setBehindContentView(R.layout.menu_frame2);
        //getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame2, viewGameFragment).commit();


    }

    private void loadHand(String url) {
        GetTasksTask getTasksTask = new GetTasksTask(GameActivity.this);
        getTasksTask.setMessageLoading("Loading Hand...");
        getTasksTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getTasksTask.execute(url + game_id + "/hand");
    }

    private void loadBlackCard(String url){
        GetTasksTask getBlackCardTask = new GetTasksTask(GameActivity.this);
        getBlackCardTask.setMessageLoading("Loading Black Card...");
        getBlackCardTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        //TODO: need to get the url for the black card
        getBlackCardTask.execute(url);
    }

    private class GetTasksTask extends UrlJsonAsyncTask {
        public GetTasksTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                JSONObject data = json.getJSONObject("data");
                JSONArray cardTexts = data.getJSONArray("texts");
                JSONArray ids = data.getJSONArray("ids");
                if(data != null){
                    for (int i = 0; i < cardTexts.length(); i++) {
                        card_texts.add(cardTexts.getString(i));
                        card_ids.add(ids.getInt(i));
                    }
                    bundle.putStringArrayList("card_texts", card_texts);
                    bundle.putIntegerArrayList("card_ids", card_ids);
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
