package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewScoreFragment extends Fragment {
    CardUI cardView;
    CardStack cardStack;
    CardUI blackCardView;
    SharedPreferences mPreferences;
    private int game_id = -1;
    private static final String HAND_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games/";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.activity_view_game);
        mPreferences = getActivity().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        game_id = getActivity().getIntent().getIntExtra("gameId", -1);
        cardView = (CardUI)getActivity().findViewById(R.id.cards_view);
        cardView.setSwipeable(false);
        //blackCardView.setSwipeable(false);
        //cardStack.setTitle("Cards to Submit");

        loadHand(HAND_URL);

        return inflater.inflate(R.layout.activity_view_game, container, false);
    }

    private void loadHand(String url) {
        GetTasksTask getTasksTask = new GetTasksTask(getActivity());
        getTasksTask.setMessageLoading("Loading Hand...");
        getTasksTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getTasksTask.execute(url + game_id + "/hand");
    }

    private void loadBlackCard(String url){
        GetTasksTask getBlackCardTask = new GetTasksTask(getActivity());
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
                JSONArray jsonTasks = data.getJSONArray("texts");
                JSONArray card_ids = data.getJSONArray("ids");
                for (int i = 0; i < jsonTasks.length(); i++) {
                    cardView.addCard(new CustomCard((Integer)card_ids.get(i),(String)jsonTasks.get(i)));

                }
                cardView.refresh();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }

    //TODO: implement this
    private void addCardToStack(){

    }

}
