package com.cs.usagainsthumanity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.cs.usagainsthumanity.Adapters.CardAdapter;
import com.cs.usagainsthumanity.Adapters.SubmittedAdapter;
import com.cs.usagainsthumanity.Objects.CardObj;
import com.cs.usagainsthumanity.Objects.Submitted;
import com.savagelook.android.UrlJsonAsyncTask;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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

public class ViewGameFragment extends SherlockListFragment {
    ArrayList<Integer> selected = new ArrayList<Integer>();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getSherlockActivity());
        excluded = new ArrayList<Integer>();
        game_id = getActivity().getIntent().getIntExtra("gameID", -1);
        //CardStack stack = new CardStack();
        //stack.setTitle("Herp");
        //stack.add(new CustomCard(0, "Test Card"));
        //cardView.addStack(stack);
        View blackView = getLayoutInflater(getArguments()).inflate(R.layout.black_card, null);
        TextView blackText = (TextView) blackView.findViewById(R.id.text);
        blackText.setText(getArguments().getString("blackCardText"));
        getListView().addHeaderView(blackView);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);



        card_texts = getArguments().getStringArrayList("card_texts");
        card_ids = getArguments().getIntegerArrayList("card_ids");
        game_id = getArguments().getInt("gameID");
        is_czar = getArguments().getBoolean("is_czar");
        if(is_czar){
            ArrayList<Submitted> subs = (ArrayList<Submitted>) getArguments().getSerializable("submitted");
            if(!subs.isEmpty()){
                Collections.shuffle(subs);
                setListAdapter(new SubmittedAdapter(getSherlockActivity(), R.layout.submitted_game_item, subs));
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
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
                                        winning_id = (int) id;
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
            }
        }else{
            ArrayList<CardObj> cardObjs = new ArrayList<CardObj>();
            for(int i = 0; i < card_texts.size(); i++){
                cardObjs.add(new CardObj(card_ids.get(i), card_texts.get(i)));
            }
            setListAdapter(new CardAdapter(getSherlockActivity(), R.layout.custom_card, cardObjs));
            AdapterView.OnItemClickListener OILCL = new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> l, View v,
                                               final int position, long id) {


                    SparseBooleanArray sba = getListView().getCheckedItemPositions();
                    selected.clear();
                    for(int i = 1; i < getListView().getAdapter().getCount(); i++){
                        if (sba.get(i)){
                            selected.add(i - 1);
                        }
                    }
                    if(selected.size() > 0){
                        if(mMode == null){
                            mMode = getSherlockActivity().startActionMode(mActionModeCallback);
                        }
                    }else{
                        if(mMode!= null){
                            mMode.finish();
                        }
                    }
                }

            };
            getListView().setOnItemClickListener(OILCL);
        }
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
            if(selected.size() > 0){
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

                                SparseBooleanArray sparseBooleanArray = getListView().getCheckedItemPositions();
                                cardIDS = new int[selected.size()];
                                for (int i = 0; i < selected.size(); i++) {
                                    cardIDS[i] = card_ids.get(selected.get(i));
                                }
                                PlayCardTask createGameTask = new PlayCardTask(getSherlockActivity());
                                createGameTask.setMessageLoading("Submitting Cards...");
                                //createGameTask.setAuthToken(mPreferences.getString("AuthToken", ""));
                                String url = CREATE_GAME_URL + "/" + game_id + "/whitecard" + "?auth_token=" + mPreferences.getString("AuthToken", "");
                                createGameTask.execute(url);

                            }
                        })
                        .create();
                alertDialog.show();

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