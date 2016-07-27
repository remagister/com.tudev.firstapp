package com.tudev.firstapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import java.io.Serializable;

/**
 * Created by Саша on 27.07.2016.
 */
public class ContactListener implements AdapterView.OnItemClickListener {

    private Context internalContext;

    public ContactListener(Context context){
        internalContext = context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(internalContext, ContactActivity.class);
        intent.putExtra(internalContext.getString(R.string.contact_bundle_key),
                (Serializable)adapterView.getItemAtPosition(i));
        internalContext.startActivity(intent);
    }
}
