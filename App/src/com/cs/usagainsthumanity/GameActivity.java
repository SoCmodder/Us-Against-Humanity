package com.cs.usagainsthumanity;

import android.os.Bundle;
import com.slidingmenu.lib.SlidingMenu;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 2:51 PM
 */
public class GameActivity extends BaseSlideActivity {

    public GameActivity() {
        super(R.string.view_game_title);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        setContentView(R.layout.menu_frame);

        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new ViewScoreFragment()).commit();

    }


}
