package com.cs.usagainsthumanity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class ViewGameHistoryActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_game_history);

        // Show the Up button in the action bar.

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(ViewGameHistoryFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(ViewGameHistoryFragment.ARG_ITEM_ID, 0));
            ViewGameHistoryFragment fragment = new ViewGameHistoryFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.history_fragment, fragment).commit();
        }


    }


}
