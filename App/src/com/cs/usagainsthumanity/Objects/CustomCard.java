package com.cs.usagainsthumanity.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.cs.usagainsthumanity.R;
import com.fima.cardsui.objects.Card;

/**
 * Created with IntelliJ IDEA.
 * User: socmodder
 * Date: 4/11/13
 * Time: 8:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomCard extends Card {

    String text;
    Integer id;

    public CustomCard(Integer id ,String text){
        this.text = text;
        this.id = id;
    }

    public Integer getID() {
        return id;
    }


    @Override
    public View getCardContent(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_card, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);

        return view;
    }
}
