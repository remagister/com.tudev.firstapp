package com.tudev.firstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * Created by Саша on 27.07.2016.
 */
public class ContactAdapter extends BaseAdapter {

    private Contact internalContacts[];
    private Context internalContext;

    public ContactAdapter(Context context, Contact contacts[]){
        internalContext = context;
        internalContacts = contacts;
    }

    @Override
    public int getCount() {
        return internalContacts.length;
    }

    @Override
    public Object getItem(int i) {
        return internalContacts[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) internalContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.contact_view, viewGroup, false);  // do not attach to root
        }
        TextView name = (TextView)view.findViewById(R.id.nameText);
        TextView email = (TextView)view.findViewById(R.id.descrText);
        name.setText(internalContacts[i].getName());
        email.setText(internalContacts[i].getEmail());
        return view;
    }
}
