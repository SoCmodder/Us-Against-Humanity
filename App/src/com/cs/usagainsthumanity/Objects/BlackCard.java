package com.cs.usagainsthumanity.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.cs.usagainsthumanity.R;
import com.fima.cardsui.objects.Card;

/**
 * Created with IntelliJ IDEA.
 * User: Stewart
 * Date: 4/25/13
 * Time: 1:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class BlackCard extends Card {

    String text;

    public BlackCard(String text){
        this.text = text;
    }

    @Override
    public View getCardContent(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.black_card, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);

        return view;
    }
}
