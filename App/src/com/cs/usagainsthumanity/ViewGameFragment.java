package com.cs.usagainsthumanity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;
import com.savagelook.android.UrlJsonAsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: SoCmodder (Mitch Miller)
 * Date: 4/9/13
 * Time: 7:48 PM
 * Description: Activity for viewing the currently selected game, yo.
 */
    /*TODO: need to get the amount of cards allowed to play for each turn so that users can't submit
    more than is allowed.*/
    //TODO: Implement onActivityCreated method

public class ViewGameFragment extends SherlockFragment {
    CardUI cardView;
    CardStack cardStack;
    CardUI blackCardView;
    SharedPreferences mPreferences;
    ArrayList<String> card_texts;
    ArrayList<Integer> card_ids;
    private int game_id = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_view_game, container, false);
        //mPreferences = getActivity().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        game_id = getActivity().getIntent().getIntExtra("gameID", -1);
        cardView = (CardUI)v.findViewById(R.id.cards_view);
        cardView.setSwipeable(false);


        //CardStack stack = new CardStack();
        //stack.setTitle("Herp");
        //stack.add(new CustomCard(0, "Test Card"));
        //cardView.addStack(stack);
        card_texts = getArguments().getStringArrayList("card_texts");
        card_ids = getArguments().getIntegerArrayList("card_ids");
        game_id = getArguments().getInt("gameID");
        addCards();
        cardView.refresh();

        return v;
    }

    public void addCards(){
        for(int i=0; i<card_texts.size(); i++){
            cardView.addCard(new CustomCard(card_ids.get(i), card_texts.get(i)));
        }
    }

    //TODO: implement this
    private void addCardToStack(){

    }
}