package com.cs.usagainsthumanity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
        setContentView(R.layout.activity_view_game_info);
        Game game = (Game) getIntent().getSerializableExtra("game");
        TextView hostname = (TextView) findViewById(R.id.host_name);
        hostname.setText(game.getHostName());
        ScoreAdapter mAdapter = new ScoreAdapter(ViewGameInfoActivity.this, game.getPlayerList());
        ListView lv = (ListView) findViewById(R.id.playerlist);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        //TODO: Async last round

    }
}
