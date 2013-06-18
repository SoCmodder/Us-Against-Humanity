package com.cs.blackandwhite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder
 * Date: 3/19/13
 * Time: 2:48 PM
 */
public class WelcomeActivity extends SherlockActivity {
    SharedPreferences mPreferences;
    AlertDialog notice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);;

        checkVersion();

        findViewById(R.id.registerButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // No account, load new account view
                        Intent intent = new Intent(WelcomeActivity.this,
                                RegisterActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

        findViewById(R.id.loginButton).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Existing Account, load activity_login view
                        Intent intent = new Intent(WelcomeActivity.this,
                                LoginActivity.class);
                        startActivityForResult(intent, 0);
                    }
                });

        if(!mPreferences.contains("notice") || !mPreferences.contains("AuthToken")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
            builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setNegativeButton("Got it!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            notice = builder.create();
        }

        if(!mPreferences.contains("notice")){
            notice.show();
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt("notice", 1);
            editor.commit();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void checkVersion(){
        int vNumber = 0;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            vNumber = pInfo.versionCode;

            if(vNumber <= 10 && !mPreferences.contains("fixed")){
                SharedPreferences.Editor editor =
                        getApplicationContext().getSharedPreferences("AuthToken", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.putBoolean("fixed", true);
                editor.commit();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finish();
    }
}