package com.cs.usagainsthumanity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.cs.usagainsthumanity.Objects.Game;
import com.cs.usagainsthumanity.dummy.DummyContent;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/3/13
 * Time: 8:17 AM
 */
public class OpenGamesActivity extends SherlockListActivity {

    private ArrayList<Game> gameList;
    private GameArrayAdapter mAdapter = null;
    private ProgressDialog pd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        pd = new ProgressDialog(this);
        pd.show();
        mAdapter = new GameArrayAdapter(OpenGamesActivity.this, DummyContent.DummyGames);
		getListView().setAdapter(mAdapter);
		pd.dismiss();
        //new LoadGameTask().execute();
    }
    
    private class LoadGameTask extends AsyncTask <Void, Map<String, Object>, Map<String, Object>>{

		@Override
		protected Map<String, Object> doInBackground(Void... arg0) {
			gameList = new ArrayList<Game>();
			Map<String, Object> result = null;
			try {
				 result = Data.getRequest("games/?find=open");
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(Map<String, Object> result){
			ArrayList<Map<String, Object>> games = (ArrayList<Map<String, Object>>) result.get("games");	
			for(Map<String, Object> game : games){
				//gameList.add(new Game(game));
			}
			mAdapter = new GameArrayAdapter(OpenGamesActivity.this, DummyContent.DummyGames);
			getListView().setAdapter(mAdapter);
			pd.dismiss();
		}
    	
    }
}