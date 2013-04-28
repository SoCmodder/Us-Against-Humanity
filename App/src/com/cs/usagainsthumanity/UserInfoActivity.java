package com.cs.usagainsthumanity;


import android.preference.PreferenceManager;
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
    private static final String WHO_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/who";
    private static final String USERS_URL = "http://r06sjbkcc.device.mst.edu:3000/api/v1/users/";
    private SharedPreferences mPreferences;
    
	public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_view_profile);
        Integer userid = getIntent().getIntExtra("user_id", -1);
        if(userid == -1)
        	loadWhoFromAPI(WHO_URL);
        else
        	loadUserInfoFromAPI(USERS_URL+userid.toString());
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
            		usernameField.setText(json.getJSONObject("data").getString("name")+"'s Profile");
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
