package com.tudev.firstapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tudev.firstapp.data.Contact;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Саша on 27.07.2016.
 */
public class ContactAdapter extends BaseAdapter {

    private List<Contact> internalContacts;
    private LayoutInflater inflater;

    public ContactAdapter(LayoutInflater infl, List<Contact> contacts){
        inflater = infl;
        internalContacts = new ArrayList<>();
        internalContacts.addAll(contacts);
    }

    static class ViewHolder{
        TextView nameLabel;
        TextView emailLabel;
        
    }

    private ViewHolder createHolder(View view){
        ViewHolder ret = new ViewHolder();
        ret.nameLabel = (TextView)view.findViewById(R.id.nameText);
        ret.emailLabel = (TextView)view.findViewById(R.id.descrText);
        return ret;
    }

    @Override
    public int getCount() {
        return internalContacts.size();
    }

    @Override
    public Contact getItem(int i) {
        return internalContacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = inflater.inflate(R.layout.contact_view, viewGroup, false);  // do not attach to root
            holder = createHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        Contact currentContact = getItem(i);
        holder.nameLabel.setText(currentContact.getName());
        holder.emailLabel.setText(currentContact.getEmail());
        return view;
    }
}
