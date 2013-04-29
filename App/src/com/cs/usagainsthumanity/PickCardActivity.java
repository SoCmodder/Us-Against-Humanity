package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.cs.usagainsthumanity.Adapters.CardAdapter;
import com.cs.usagainsthumanity.Objects.CardObj;
import com.cs.usagainsthumanity.Objects.Game;
import com.cs.usagainsthumanity.Objects.Player;
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

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/28/13
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PickCardActivity extends SherlockListActivity {
    String SERVER_URL = Data.serverUrl + "whitecard";
    SharedPreferences mPreferences;
    ArrayList<CardObj> cards;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadCard(SERVER_URL);

    }

    private void loadCard(String url) {
        GetCardsTask getTasksTask = new GetCardsTask(PickCardActivity.this);
        getTasksTask.setMessageLoading("Loading Cards...");
        getTasksTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getTasksTask.execute(url);
    }

    private class GetCardsTask extends UrlJsonAsyncTask {
        public GetCardsTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                JSONObject data = json.getJSONObject("data");
                JSONArray whiteCards = data.getJSONArray("whitecards");
                int length = whiteCards.length();
                cards = new ArrayList<CardObj>();
                for(int i = 0; i < length; i++){
                    JSONObject card = whiteCards.getJSONObject(i);
                    cards.add(new CardObj(card.getInt("id"), card.getString("text")));
                }
                setListAdapter(new CardAdapter(PickCardActivity.this, R.layout.custom_card, cards));
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        PickCardTask createGameTask = new PickCardTask(PickCardActivity.this);
                        createGameTask.setMessageLoading("Submitting Cards...");
                        //createGameTask.setAuthToken(mPreferences.getString("AuthToken", ""));
                        createGameTask.execute(SERVER_URL + "/" + cards.get(position).getId() + "?auth_token=" + mPreferences.getString("AuthToken", ""));
                    }
                });
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }

    private class PickCardTask extends UrlJsonAsyncTask {
        public PickCardTask(Context context) {
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

                    // setup the returned values in case
                    // something goes wrong
                    json.put("success", false);
                    json.put("info", "Something went wrong. Retry!");
                    // add the user email and password to
                    // the params
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
                    PickCardActivity.this.finish();
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
