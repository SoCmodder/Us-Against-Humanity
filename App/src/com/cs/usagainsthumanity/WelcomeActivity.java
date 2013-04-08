package com.cs.usagainsthumanity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder
 * Date: 3/19/13
 * Time: 2:48 PM
 */
public class WelcomeActivity extends SherlockActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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