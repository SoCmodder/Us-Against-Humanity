package com.cs.blackandwhite.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.cs.blackandwhite.Objects.GameRound;
import com.cs.blackandwhite.Objects.Submitted;
import com.cs.blackandwhite.R;
import com.emilsjolander.components.stickylistheaders.StickyListHeadersAdapter;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/11/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameRoundAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {

    List<GameRound> gameRounds;
    Submitted[] submitteds;
    int layoutResourceId;
    Context mContext;

    private LayoutInflater inflater;

    public GameRoundAdapter(Context context, int layoutResourceId, List<GameRound> gameRounds) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.layoutResourceId = layoutResourceId;
        this.gameRounds = gameRounds;
        int length = 0;
        for (GameRound gameRound :gameRounds){
            length += gameRound.getSubmittedList().size();
        }
        submitteds = new Submitted[length];
        int i = 0;
        for(GameRound gameRound : gameRounds){
            for (Submitted submitted : gameRound.getSubmittedList()){
                submitteds[i] = submitted;
                i++;
            }
        }
    }

    @Override
    public int getCount() {
        return submitteds.length;
    }

    @Override
    public Object getItem(int position) {
        return submitteds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {

        SubmittedHolder holder = null;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new SubmittedHolder();
            holder.username = (TextView)convertView.findViewById(R.id.user_name);
            holder.ll = (LinearLayout)convertView.findViewById(R.id.inner_view);

            convertView.setTag(holder);
        }  else {
            holder = (SubmittedHolder) convertView.getTag();
        }



        Submitted submittedItem = submitteds[position];
        holder.ll.removeAllViews();
        holder.username.setText(submittedItem.getUsername());

        for(String text : submittedItem.getSubmitted()){
            LayoutInflater inflater2 = LayoutInflater.from(mContext);
            View v2 = inflater2.inflate(R.layout.custom_card, holder.ll, false);
            ((TextView)v2.findViewById(R.id.text)).setText(text);
            holder.ll.addView(v2);
        }

        return convertView;

    }

    static class SubmittedHolder{
        TextView username;
        LinearLayout ll;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.black_card_game_round_item, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.winninguser = (TextView) convertView.findViewById(R.id.winning_user);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        Submitted submitted = submitteds[position];
        GameRound gRound = null;
        for(GameRound gameRound : gameRounds){
            if(gameRound.getId() == submitted.getGameround_id()){
                gRound = gameRound;
                break;
            }
        }

        holder.text.setText(gRound.getBlacktext());
        holder.winninguser.setText("Winner: " + gRound.getWinninguser());
        return convertView;
    }

    //remember that these have to be static, postion=1 should always return the same Id that is.
    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return submitteds[position].getGameround_id();
    }

    class HeaderViewHolder {
        TextView text;
        TextView winninguser;
    }

    class ViewHolder {
        TextView text;
    }

    @Override
    public int getPositionForSection(int section) {
        if(section >= gameRounds.size()){
            section = gameRounds.size()-1;
        }else if(section < 0){
            section = 0;
        }

        int position = 0;
        int sectionChar = gameRounds.get(section).getId();
        for(int i = 0 ; i<submitteds.length ; i++){
            if(sectionChar == submitteds[i].getGameround_id()){
                position = i;
                break;
            }
        }
        return position;
    }

    @Override
    public int getSectionForPosition(int position) {
        if(position >= submitteds.length){
            position = submitteds.length-1;
        }else if(position < 0){
            position = 0;
        }

        return gameRounds.indexOf(submitteds[position]);
    }

    @Override
    public Object[] getSections() {
        return gameRounds.toArray(new GameRound[gameRounds.size()]);
    }

    public void clearAll() {
        submitteds = new Submitted[0];
        gameRounds.clear();
    }
}