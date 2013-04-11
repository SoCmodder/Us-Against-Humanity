package com.cs.usagainsthumanity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.cs.usagainsthumanity.Objects.Game;
import com.fima.cardsui.views.CardUI;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder (Mitch Miller)
 * Date: 4/9/13
 * Time: 7:48 PM
 * Description: Activity for viewing the currently selected game, yo.
 */
public class ViewGameActivity extends Activity {
    CardUI cardView;
    SharedPreferences mPreferences;
    private static final String HAND_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/:id/hand";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        cardView = (CardUI)findViewById(R.id.cards_view);
        cardView.setSwipeable(false);
        loadHand(HAND_URL);
    }

    private void loadHand(String url) {
        GetTasksTask getTasksTask = new GetTasksTask(ViewGameActivity.this);
        getTasksTask.setMessageLoading("Loading Hand...");
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
                JSONArray jsonTasks = data.getJSONArray("texts");
                int length = jsonTasks.length();
                List<String> tasksTitles = new ArrayList<String>(length);

                for (int i = 0; i < length; i++) {
                    tasksTitles.add(String.valueOf(jsonTasks.getJSONObject(i)));
                }

                for(int i=0; i<tasksTitles.size(); i++){
                    cardView.addCard(new CustomCard(tasksTitles.get(i)));
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