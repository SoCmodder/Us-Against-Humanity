package com.cs.blackandwhite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.savagelook.android.UrlJsonAsyncTask;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder
 * Date: 3/19/13
 * Time: 2:53 PM
 */
public class RegisterActivity extends SherlockActivity {
    private final static String REGISTER_API_ENDPOINT_URL = Data.serverUrl + "registrations";
    private SharedPreferences mPreferences;
    private String mUserEmail;
    private String mUserName;
    private String mUserPassword;
    private String mUserPasswordConfirmation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void registerNewAccount(View button) {
        EditText userEmailField = (EditText) findViewById(R.id.userEmail);
        mUserEmail = userEmailField.getText().toString();
        EditText userNameField = (EditText) findViewById(R.id.userName);
        mUserName = userNameField.getText().toString();
        EditText userPasswordField = (EditText) findViewById(R.id.userPassword);
        mUserPassword = userPasswordField.getText().toString();
        EditText userPasswordConfirmationField = (EditText) findViewById(R.id.userPasswordConfirmation);
        mUserPasswordConfirmation = userPasswordConfirmationField.getText().toString();

        if (mUserEmail.length() == 0 || mUserName.length() == 0 || mUserPassword.length() == 0 || mUserPasswordConfirmation.length() == 0) {
            // input fields are empty
            Toast.makeText(this, "Please complete all the fields",
                    Toast.LENGTH_LONG).show();
        }
        else {
            if (!mUserPassword.equals(mUserPasswordConfirmation)) {
                // password doesn't match confirmation
                userPasswordConfirmationField.setError("Passwords do not match!");
            }
            else if(mUserPassword.length() < 8){
                userPasswordField.setError("Password must be at least 8 characters.");
            }
            else {
                // everything is ok!
                RegisterTask registerTask = new RegisterTask(RegisterActivity.this);
                registerTask.setMessageLoading("Registering new account...");
                registerTask.execute(REGISTER_API_ENDPOINT_URL);
            }
        }
    }

    private class RegisterTask extends UrlJsonAsyncTask {
        public RegisterTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... urls) {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urls[0]);
            JSONObject holder = new JSONObject();
            JSONObject userObj = new JSONObject();
            String response = null;
            JSONObject json = new JSONObject();

            try {
                try {
                    // setup the returned values in case
                    // something goes wrong
                    json.put("success", false);
                    json.put("info", "Something went wrong. Retry!");

                    // add the users's info to the post params
                    userObj.put("email", mUserEmail);
                    userObj.put("name", mUserName);
                    userObj.put("password", mUserPassword);
                    userObj.put("password_confirmation", mUserPasswordConfirmation);
                    holder.put("user", userObj);
                    StringEntity se = new StringEntity(holder.toString());
                    post.setEntity(se);

                    // setup the request headers
                    post.setHeader("Accept", "application/json");
                    post.setHeader("Content-Type", "application/json");

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    response = client.execute(post, responseHandler);
                    json = new JSONObject(response);

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                    Log.e("ClientProtocol", "" + e);
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
                    SharedPreferences.Editor editor = mPreferences.edit();
                    // save the returned auth_token into
                    // the SharedPreferences
                    editor.putString("AuthToken", json.getJSONObject("data").getString("auth_token"));
                    editor.commit();

                    // launch the HomeActivity and close this one
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(context, json.getString("info"), Toast.LENGTH_LONG).show();
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