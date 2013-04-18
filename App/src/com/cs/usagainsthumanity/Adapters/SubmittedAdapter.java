package com.cs.usagainsthumanity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.cs.usagainsthumanity.Objects.CustomCard;
import com.cs.usagainsthumanity.Objects.GameRound;
import com.cs.usagainsthumanity.Objects.Submitted;
import com.cs.usagainsthumanity.R;
import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/18/13
 * Time: 4:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class SubmittedAdapter extends BaseAdapter {


    private List<Submitted> submitted;
    private LayoutInflater inflator;
    private Context mContext;

    public SubmittedAdapter(Context mContext, List<Submitted> submitted){
        this.submitted = submitted;
        this.mContext = mContext;
        this.inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return submitted.size();
    }

    @Override
    public Object getItem(int position) {
        return submitted.get(position);
    }

    @Override
    public long getItemId(int position) {
        return submitted.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflator.inflate(R.layout.submitted_game_item, parent, false);
        }
        Submitted sub = submitted.get(position);
        TextView gameuser = (TextView) v.findViewById(R.id.user_name);
        gameuser.setText(String.valueOf(sub.getGameuserId()));
        CardUI mView =  (CardUI) v.findViewById(R.id.cards_view);
        CardStack mStack = new CardStack();
        for(String text : sub.getSubmitted()){
            mStack.add(new CustomCard(sub.getGameuserId(), text));
        }
        mView.addStack(mStack);
        mView.refresh();
        return v;
    }
}
