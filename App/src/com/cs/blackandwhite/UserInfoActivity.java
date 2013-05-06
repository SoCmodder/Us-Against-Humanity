package com.cs.blackandwhite;


import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.savagelook.android.UrlJsonAsyncTask;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoActivity extends SherlockActivity {
    private static final String WHO_URL = Data.serverUrl + "who";
    private static final String USERS_URL = Data.serverUrl + "users/";
    private SharedPreferences mPreferences;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_view_profile);
        Button button = (Button)findViewById(R.id.pickCard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserInfoActivity.this, PickCardActivity.class));
            }
        });
        Integer userid = getIntent().getIntExtra("user_id", -1);
        if(userid != -1){
            button.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Integer userid = getIntent().getIntExtra("user_id", -1);
        if(userid == -1)
            loadWhoFromAPI(WHO_URL);
        else{
            loadUserInfoFromAPI(USERS_URL+userid);
            loadUserStatFromApi(USERS_URL + userid + "/stats");
        }
    }

    private void loadUserStatFromApi(String url){
        GetStatTask getStatTask = new GetStatTask(UserInfoActivity.this);
        getStatTask.setMessageLoading("Loading Stats...");
        getStatTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getStatTask.execute(url);
    }

    private class GetStatTask extends UrlJsonAsyncTask {
        public GetStatTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if((json.getString("success").equals("true"))){
                    JSONObject data = json.getJSONObject("data");
                    TextView gamePlayedField = (TextView) findViewById(R.id.gamesPlayed);
                    gamePlayedField.setText(Integer.toString(data.getInt("played")));
                    TextView wonField = (TextView) findViewById(R.id.gamesWon);
                    wonField.setText(Integer.toString(data.getInt("won")));
                    TextView cardText = (TextView) findViewById(R.id.text);
                    String cardStringText = data.getString("favorite");
                    if(cardStringText != "null"){
                        cardText.setText(cardStringText);
                    }else{
                        cardText.setText("No Card Selected");
                    }
                }
                else
                    Toast.makeText(context, json.getString("info"),Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }


    private void loadWhoFromAPI(String url){
        GetWhoTask getWhoTask = new GetWhoTask(UserInfoActivity.this);
        getWhoTask.setMessageLoading("Loading profile...");
        getWhoTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getWhoTask.execute(url);
    }

    private class GetWhoTask extends UrlJsonAsyncTask {
        public GetWhoTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if((json.getString("success").equals("true"))){
                    JSONObject data = json.getJSONObject("data");
                    TextView usernameField = (TextView) findViewById(R.id.username);
                    usernameField.setText(data.getJSONObject("user").getString("name")+"'s Profile");
                    loadUserStatFromApi(USERS_URL+data.getJSONObject("user").getInt("id")+"/stats");

                }
                else
                    Toast.makeText(context, json.getString("info"),Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }

    private void loadUserInfoFromAPI(String url){
        GetUserInfoTask getUserInfoTask = new GetUserInfoTask(UserInfoActivity.this);
        getUserInfoTask.setMessageLoading("Loading profile...");
        getUserInfoTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        getUserInfoTask.execute(url);
    }

    private class GetUserInfoTask extends UrlJsonAsyncTask {
        public GetUserInfoTask(Context context) {
            super(context);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                TextView usernameField = (TextView) findViewById(R.id.username);
                if((json.getString("success").equals("true")))
                    usernameField.setText(json.getJSONObject("data").getJSONObject("user").getString("name")+"'s Profile");
                else
                    Toast.makeText(context, json.getString("info"),Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(),
                        Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
