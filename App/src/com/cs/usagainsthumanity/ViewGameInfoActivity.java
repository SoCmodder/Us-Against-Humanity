package com.cs.usagainsthumanity;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.cs.usagainsthumanity.Objects.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/10/13
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */

public class ViewGameInfoActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Game game = (Game) getIntent().getSerializableExtra("game");
    }
}
