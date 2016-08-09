package com.tudev.firstapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tudev.firstapp.R;
import com.tudev.firstapp.adapter.ContactAdapterState;
import com.tudev.firstapp.adapter.StateAdapter;
import com.tudev.firstapp.data.dao.Contact;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Саша on 27.07.2016.
 */
public class ContactAdapter extends StateAdapter<ContactAdapterState> {

    private List<Contact.ContactSimple> internalContacts;

    public ContactAdapter(LayoutInflater infl, List<Contact.ContactSimple> contacts){
        super(ContactAdapterState.NORMAL, infl);
        internalContacts = new ArrayList<>();
        internalContacts.addAll(contacts);
    }

    static class ViewHolder{
        TextView nameLabel;
        TextView infoLabel;
        ImageView imageView;
        CheckBox checkBox;
    }

    private ViewHolder createHolder(View view){
        ViewHolder ret = new ViewHolder();
        ret.nameLabel = (TextView) view.findViewById(R.id.nameText);
        ret.infoLabel = (TextView) view.findViewById(R.id.descrText);
        ret.imageView = (ImageView) view.findViewById(R.id.contactThumb);
        ret.checkBox = (CheckBox) view.findViewById(R.id.checkableView_selection);
        return ret;
    }

    @Override
    public int getCount() {
        return internalContacts.size();
    }

    @Override
    public Contact.ContactSimple getItem(int i) {
        return internalContacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    protected final View getViewState(int i, View view, ViewGroup group, ContactAdapterState state) {
        ViewHolder holder;
        if(view == null){
            view = getInternalInflater().inflate(R.layout.contact_view, group, false);  // do not attach to root
            holder = createHolder(view);
            view.setTag(holder);
            view.setSelected(true);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        Contact.ContactSimple currentContact = getItem(i);
        holder.nameLabel.setText(currentContact.getName());
        holder.infoLabel.setText(currentContact.getPhone());
        switch (state){
            case NORMAL: {
                holder.checkBox.setVisibility(View.GONE);
                break;
            }
            case SELECTION:{
                holder.checkBox.setVisibility(View.VISIBLE);
                break;
            }
        }
        // TODO: 09.08.16 SET CONTACT THUMBNAIL
        return view;
    }

}
