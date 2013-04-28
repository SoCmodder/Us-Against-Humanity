package com.cs.usagainsthumanity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cs.usagainsthumanity.Objects.Submitted;
import com.cs.usagainsthumanity.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/18/13
 * Time: 4:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class SubmittedAdapter extends ArrayAdapter<Submitted> {

    private List<Submitted> submitted;
    private int layoutResourceId;
    private Context mContext;

    public SubmittedAdapter(Context mContext, int layoutResourceId, List<Submitted> submitted){
        super(mContext, layoutResourceId, submitted);
        this.submitted = submitted;
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return submitted.size();
    }

    @Override
    public long getItemId(int arg0) {
        return submitted.get(arg0).getGameuserId();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View v = arg1;
        SubmittedHolder holder = null;
        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(layoutResourceId, arg2, false);
            v.setTag(holder);
        }
        LinearLayout ll = (LinearLayout)v.findViewById(R.id.inner_view);
        holder = new SubmittedHolder();
        holder.username = (TextView)v.findViewById(R.id.user_name);
        Submitted submittedItem = submitted.get(arg0);
        holder.username.setText("User #" + arg0);
        for(String text : submittedItem.getSubmitted()){
            LayoutInflater inflater2 = LayoutInflater.from(mContext);
            View v2 = inflater2.inflate(R.layout.custom_card, ll, false);
            ((TextView)v2.findViewById(R.id.text)).setText(text);
            ll.addView(v2);
        }
        return v;

    }

    static class SubmittedHolder{
        TextView username;
    }
}
