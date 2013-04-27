package com.cs.usagainsthumanity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockPreferenceActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsActivity extends SherlockPreferenceActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        Button button = new Button(this);
        button.setText("Logout");
        final SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.remove("AuthToken");
                editor.commit();
                SettingsActivity.this.finish();

            }
        });
        getListView().addFooterView(button);
    }
}
