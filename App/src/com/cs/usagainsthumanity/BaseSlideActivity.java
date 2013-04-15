package com.cs.usagainsthumanity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Development
 * Date: 4/14/13
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseSlideActivity extends SlidingFragmentActivity {

    private int mTitleRes;
    protected ViewGameFragment mFrag;

    public BaseSlideActivity(int titelRes){
        mTitleRes = titelRes;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(mTitleRes);

        setBehindContentView(R.layout.menu_frame);
        if(savedInstanceState == null){
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new ViewGameFragment();
            t.replace(R.id.menu_frame, mFrag);
            t.commit();

        }
        else{
            //do something else
        }

        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }
}