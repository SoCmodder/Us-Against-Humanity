package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.cs.usagainsthumanity.Objects.GameRound;
import com.cs.usagainsthumanity.Objects.Submitted;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewGameHistoryFragment extends SherlockFragment{
    public static final String ARG_ITEM_ID = "Game ID";
    private CardUI mView;
    private SharedPreferences mPreferences;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPreferences = getSherlockActivity().getSharedPreferences("CurrentUser", getSherlockActivity().MODE_PRIVATE);
        View v = inflater.inflate(R.layout.fragment_game_history, container, false);
        mView = (CardUI) v.findViewById(R.id.cardview);
        int game_id = getArguments().getInt(ARG_ITEM_ID);
        loadHistoryFromAPI(Data.serverUrl + "games/" + game_id + "/history");


        return v;
    }


    private void loadHistoryFromAPI(String url){
        GetTasksTask getTasksTask = new GetTasksTask(getSherlockActivity());
        getTasksTask.setMessageLoading("Loading History");
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
                JSONArray rounds = data.getJSONArray("rounds");
                List<GameRound> gameRounds = new ArrayList<GameRound>();
                int length = rounds.length();
                for(int i = 0; i < length; i++){
                    gameRounds.add(new GameRound(rounds.getJSONObject(i)));
                }
                //TODO Everything after this line needs to be made into a function
                for(GameRound gameRound : gameRounds){
                    CardStack temp = new CardStack();
                    temp.setTitle(gameRound.getBlacktext() + "\n" + Integer.toString(gameRound.getWinninguser()));
                    mView.addStack(temp);
                    for(Submitted sub : gameRound.getSubmittedList()){
                        temp = new CardStack();
                        temp.setTitle(String.valueOf(sub.getGameuserId()));

                        for(String text : sub.getSubmitted()){
                            temp.add(new CustomCard(sub.getGameuserId(), text));
                        }
                        mView.addStack(temp);

                    }
                }

                mView.refresh();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }


}
