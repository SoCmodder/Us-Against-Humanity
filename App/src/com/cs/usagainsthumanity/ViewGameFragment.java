package com.cs.usagainsthumanity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.cs.usagainsthumanity.Objects.BlackCard;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.cs.usagainsthumanity.Objects.Submitted;
import com.fima.cardsui.objects.Card;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.savagelook.android.UrlJsonAsyncTask;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder (Mitch Miller)
 * Date: 4/9/13
 * Time: 7:48 PM
 * Description: Activity for viewing the currently selected game, yo.
 */
    /*TODO: need to get the amount of cards allowed to play for each turn so that users can't submit
    more than is allowed.*/
//TODO: Implement onActivityCreated method

public class ViewGameFragment extends SherlockFragment {
    CardUI cardView;
    CardStack submitStack;
    SharedPreferences mPreferences;
    ArrayList<String> card_texts;
    ArrayList<Integer> card_ids;
    ArrayList<Integer> excluded;
    private  boolean is_czar;
    private int winning_id;
    private int game_id = -1;
    private int[] cardIDS;
    private static final String CREATE_GAME_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/games";
    ActionMode mMode = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_view_game, container, false);
        mPreferences = getActivity().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        excluded = new ArrayList<Integer>();
        game_id = getActivity().getIntent().getIntExtra("gameID", -1);
        cardView = (CardUI)v.findViewById(R.id.cards_view);
        cardView.setSwipeable(false);


        //CardStack stack = new CardStack();
        //stack.setTitle("Herp");
        //stack.add(new CustomCard(0, "Test Card"));
        //cardView.addStack(stack);
        card_texts = getArguments().getStringArrayList("card_texts");
        card_ids = getArguments().getIntegerArrayList("card_ids");
        game_id = getArguments().getInt("gameID");
        is_czar = getArguments().getBoolean("is_czar");
        if(is_czar){
            CardStack blackStack = new CardStack();
            blackStack.add(new BlackCard(getArguments().getString("blackCardText")));
            cardView.addStack(blackStack);
            cardView.setSwipeable(true);
            ArrayList<Submitted> subs = (ArrayList<Submitted>) getArguments().getSerializable("submitted");
            addSubmittedCards(subs);

        }else{
            addCards();
        }


        cardView.refresh();

        return v;
    }

    private void addSubmittedCards(ArrayList<Submitted> subs) {
        for(Submitted submitted:subs){
            CardStack temp = new CardStack();
            for(String text: submitted.getSubmitted()){
                final CustomCard customCard = new CustomCard(submitted.getGameuserId(), text);
                customCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getSherlockActivity())
                                .setTitle("Choose this as the winning cards?")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        winning_id = customCard.getID();
                                        WinCardTask createGameTask = new WinCardTask(getSherlockActivity());
                                        createGameTask.setMessageLoading("Submitting Cards...");
                                        //createGameTask.setAuthToken(mPreferences.getString("AuthToken", ""));
                                        createGameTask.execute(CREATE_GAME_URL + "/" + game_id + "/winningcard" + "?auth_token=" + mPreferences.getString("AuthToken", ""));

                                    }
                                })
                                .create();
                        alertDialog.show();

                    }
                });
                temp.add(customCard);
            }
            cardView.addStack(temp);
        }
        cardView.refresh();

    }

    public void addCards(){
        CardStack blackStack = new CardStack();
        blackStack.add(new BlackCard(getArguments().getString("blackCardText")));
        cardView.addStack(blackStack);
        submitStack = new CardStack();
        submitStack.setTitle("Select " + (getArguments().getInt("blackCardNum") - excluded.size()) + " cards");
        for(Integer id : excluded){
            final CustomCard customCard = new CustomCard(id, card_texts.get(card_ids.indexOf(id)));
            customCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    excluded.remove(excluded.indexOf(customCard.getID()));
                    if(excluded.isEmpty()){
                        mMode.finish();
                        mMode = null;
                    }
                    cardView.clearCards();
                    addCards();
                }
            });
            submitStack.add(customCard);
        }
        cardView.addStack(submitStack);
        for(int i=0; i<card_texts.size(); i++){
            if(!excluded.contains(card_ids.get(i))){
                final CustomCard temp = new CustomCard(card_ids.get(i), card_texts.get(i));
                temp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mMode == null){
                            mMode = getSherlockActivity().startActionMode(mActionModeCallback);
                        }
                        selectCard(temp.getID());
                    }
                });
                cardView.addCard(temp);
            }
        }
    }

    private void selectCard(Integer id) {
        cardView.clearCards();
        excluded.add(id);
        addCards();
        //To change body of created methods use File | Settings | File Templates.
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return true;
        }

        // Called when the user exits the action mode
        public void onDestroyActionMode(ActionMode mode) {
            if (!excluded.isEmpty()){
                AlertDialog alertDialog = new AlertDialog.Builder(getSherlockActivity())
                        .setTitle("Submit Selected Cards?")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ArrayList<Card> cards = submitStack.getCards();
                                cardIDS = new int[cards.size()];
                                int i = cards.size() - 1;
                                for (Card card : cards) {
                                    CustomCard customCard = (CustomCard) card;
                                    cardIDS[i] = customCard.getID();
                                    i--;
                                }
                                PlayCardTask createGameTask = new PlayCardTask(getSherlockActivity());
                                createGameTask.setMessageLoading("Submitting Cards...");
                                //createGameTask.setAuthToken(mPreferences.getString("AuthToken", ""));
                                createGameTask.execute(CREATE_GAME_URL + "/" + game_id + "/whitecard"+ "?auth_token=" + mPreferences.getString("AuthToken", ""));

                            }
                        })
                        .create();
                alertDialog.show();
                excluded.clear();
            }
            mMode = null;
        }
    };


    private class PlayCardTask extends UrlJsonAsyncTask {
        public PlayCardTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(urls[0]);

            JSONObject gameObj = new JSONObject();
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
                    for(int i = 0; i < cardIDS.length; i++){
                        holder.put(cardIDS[i]);
                    }
                    JSONObject temp = new JSONObject();
                    temp.put("card_id", holder);
                    StringEntity se = new StringEntity(temp.toString());
                    put.setEntity(se);

                    // setup the request headers
                    put.setHeader("Accept", "application/json");
                    put.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(put, responseHandler);
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
                    Toast.makeText(getSherlockActivity(), "Success", Toast.LENGTH_SHORT).show();
                    getSherlockActivity().finish();
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

    private class WinCardTask extends UrlJsonAsyncTask {
        public WinCardTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPut put = new HttpPut(urls[0]);
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {

                    JSONObject temp = new JSONObject();
                    temp.put("user_id", winning_id);
                    StringEntity se = new StringEntity(temp.toString());
                    put.setEntity(se);

                    // setup the request headers
                    put.setHeader("Accept", "application/json");
                    put.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(put, responseHandler);
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
                    Toast.makeText(getSherlockActivity(), "Success", Toast.LENGTH_SHORT).show();
                    getSherlockActivity().finish();
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

}