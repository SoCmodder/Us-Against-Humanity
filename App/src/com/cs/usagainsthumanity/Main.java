package com.cs.usagainsthumanity;

import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity {
    //TODO: change this later
    private static final String TASKS_URL = "http://10.0.2.2:3000/api/v1/tasks.json";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
