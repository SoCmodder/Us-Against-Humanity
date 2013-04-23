package com.cs.usagainsthumanity;

import android.os.Bundle;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 2:51 PM
 */
public class GameActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        getSlidingMenu().setMode(SlidingMenu.LEFT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        setContentView(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new ViewGameFragment()).commit();
        setBehindContentView(R.layout.menu_frame2);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame2, new ViewGameFragment()).commit();


    }


}
