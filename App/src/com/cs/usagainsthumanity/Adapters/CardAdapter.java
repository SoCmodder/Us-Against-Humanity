package com.cs.usagainsthumanity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.cs.usagainsthumanity.Objects.CardObj;
import com.cs.usagainsthumanity.Objects.Player;
import com.cs.usagainsthumanity.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/26/13
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardAdapter extends ArrayAdapter<CardObj> {

    private List<CardObj> cards;
    private int layoutResourceId;
    private Context mContext;

    public CardAdapter(Context mContext, int layoutResourceId, List<CardObj> cards){
        super(mContext, layoutResourceId, cards);
        this.cards = cards;
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public long getItemId(int arg0) {
        return cards.get(arg0).getId();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View v = arg1;
        CardHolder holder = null;
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(layoutResourceId, arg2, false);
            v.setTag(holder);
        }
        holder = new CardHolder();
        holder.text = (TextView)v.findViewById(R.id.text);
        CardObj cardObj = cards.get(arg0);
        holder.text.setText(cardObj.getText());
        //state.setTextColor(gameState == 0? android.R.color.white : gameState == 1? android.R.color.holo_green_light: android.R.color.holo_red_light);
        return v;

    }

    static class CardHolder{
        TextView text;
    }
}
